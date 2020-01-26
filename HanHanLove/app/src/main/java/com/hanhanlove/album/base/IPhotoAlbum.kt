package com.hanhanlove.album.base

import android.content.res.Resources
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.bumptech.glide.Glide
import java.lang.Exception

/**
 * create by zuyuan on 2019/12/25
 */

/*xml设置相册*/
@BindingMethods(
    BindingMethod(
        type = ViewGroup::class,
        attribute = "photo_album",
        method = "setPhotoAlbum"
    )
)
interface IPhotoAlbumXML {

    /* 设置相册 */
    fun setPhotoAlbum(arrayId: Int)

    fun defaultGetPhotos(arrayId: Int, res: Resources) : MutableList<Int> = try {
        val mutableList = mutableListOf<Int>()
        val ota = res.obtainTypedArray(arrayId)
        for (i in 0 until ota.length()) {
            mutableList.add(ota.getResourceId(i, 0))
        }
        ota.recycle()
        mutableList
    } catch (e: Exception) {
        mutableListOf()
    }
}

/*带播放效果的相册相册*/
interface IPhotoAlbum {

    /*开始轮播图片*/
    fun loopImage() {}

    /*停止轮播*/
    fun stopLoop() {}

    /*展示下一张图片*/
    fun nextImage()

    /*设置完成监听*/
    fun setOnFinishCallback(callback: OnFinishCallback) {}

}

interface OnFinishCallback {
    fun onFinish()
}

/*使用内存缓存的Glide加载图片*/
fun ImageView.glideNormalLoadImage(imgId: Int) {
    Glide.with(context).asDrawable().skipMemoryCache(false).load(imgId).into(this)
}