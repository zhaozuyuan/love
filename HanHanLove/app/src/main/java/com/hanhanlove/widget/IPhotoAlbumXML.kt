package com.hanhanlove.widget

import android.content.res.Resources
import android.widget.LinearLayout
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import java.lang.Exception

/**
 * create by zuyuan on 2019/12/25
 */

/*xml设置相册*/
@BindingMethods(
    BindingMethod(
        type = LinearLayout::class,
        attribute = "photo_album",
        method = "setPhotoAlbum"
    )
)
interface IPhotoAlbumXML  {

    /* 设置相册 */
    fun setPhotoAlbum(arrayId: Int)

    fun defaultGetPhotos(arrayId: Int, res: Resources) : MutableList<Int> {
        val mutableList = mutableListOf<Int>()
        try {
            val ota = res.obtainTypedArray(arrayId)
            for (i in 0 until ota.length()) {
                mutableList.add(ota.getResourceId(i, 0))
            }
            ota.recycle()
        } catch (e: Exception) { }
        return mutableList
    }

}