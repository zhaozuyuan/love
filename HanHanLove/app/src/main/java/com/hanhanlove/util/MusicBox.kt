package com.hanhanlove.util

import android.content.Context
import android.media.MediaPlayer
import android.media.audiofx.Visualizer
import java.lang.ref.WeakReference


/**
 * create by zuyuan on 2019/12/25
 */
object MusicBox {
    private val boxMap: MutableMap<String, PlayerReference<MediaPlayer>> = mutableMapOf()

    fun openAssetMusicWithCaptureListener(fileName: String, context: Context,
                                          listener: Visualizer.OnDataCaptureListener) {
        val canContinue = openAssetMusic(fileName, context)
        if (canContinue) {
            val playerId = boxMap[fileName]!!.get().audioSessionId
            val visualizer: Visualizer
            try {
                visualizer = Visualizer(playerId)
                visualizer.enabled = false
            } catch (e: java.lang.Exception) {
                return
            }
            boxMap[fileName]!!.visualizer = WeakReference(visualizer)

            //音乐播放结束会自动释放，但停止不会释放
            boxMap[fileName]!!.get()
                .setOnCompletionListener {
                    visualizer.enabled = false
                    visualizer.release()
                }

             //采集的精度 getCaptureSizeRange()[0]为最小值，getCaptureSizeRange()[1]为最大值
            visualizer.captureSize = Visualizer.getCaptureSizeRange()[1]
            //监听，输出哪种数据
            visualizer.setDataCaptureListener(listener,
                Visualizer.getMaxCaptureRate() / 2, false, true)
            //控制何时采集数据
            visualizer.enabled = true
        }
    }

    fun openAssetMusic(fileName: String, context: Context) : Boolean {
        closeMusic(fileName)
        return try {
            val player = MediaPlayer()
            val descriptor = context.assets.openFd(fileName)
            player.reset()
            player.setDataSource(descriptor.fileDescriptor, descriptor.startOffset,
                descriptor.length)
            player.prepare()
            player.start()

            boxMap[fileName] = PlayerReference(player)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun closeMusic(fileName: String) {
        val oldPlyer = boxMap[fileName]
        if (oldPlyer != null && !oldPlyer.isNull() && oldPlyer.get().isPlaying) {
            oldPlyer.get().stop()
            //这里需要主动释放一次
            oldPlyer.visualizer?.get()?.apply {
                enabled = false
                release()
            }
        }
    }

    class PlayerReference<T>(referent: T) : WeakReference<T>(referent) {
        var visualizer: WeakReference<Visualizer> ?= null

        fun isNull() = super.get() == null

        override fun get(): T {
            return super.get()!!
        }
    }
}