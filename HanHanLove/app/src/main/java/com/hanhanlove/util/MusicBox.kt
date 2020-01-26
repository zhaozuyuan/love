package com.hanhanlove.util

import android.content.Context
import android.media.MediaPlayer
import android.media.audiofx.Visualizer
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference


/**
 * create by zuyuan on 2019/12/25
 */
object MusicBox {

    private val boxMap: MutableMap<String, PlayerReference<MediaPlayer>> = mutableMapOf()


    fun openAssetMusic(fileName: String, context: Context) : MediaPlayer? {
        closeMusic(fileName)
        return try {
            var player = MediaPlayer()
            player.reset()
            val descriptor = context.assets.openFd(fileName)
            player.setDataSource(descriptor.fileDescriptor, descriptor.startOffset,
                descriptor.length)

            player.prepare()

            boxMap[fileName] = PlayerReference(player)
            player
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun closeMusic(fileName: String) {
        val oldPlyer = boxMap[fileName]
        try {
            if (oldPlyer != null && !oldPlyer.isNull() && oldPlyer.get().isPlaying) {
                oldPlyer.get().stop()
                oldPlyer.get().release()
            }
        } catch (e: java.lang.Exception) { }

    }

    class PlayerReference<T>(referent: T) : WeakReference<T>(referent) {
        var visualizer: WeakReference<Visualizer> ?= null

        fun isNull() = super.get() == null

        override fun get(): T {
            return super.get()!!
        }
    }
}