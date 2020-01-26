package com.hanhanlove.util

import android.content.Context

object SPUtil {

    const val ALBUM_SP = "album"

    //0 1 2
    const val MODEL_NAME = "model"

    //0 1 2
    const val MODEL_TEXT_NAME = "text_model"

    //开启321
    const val MODEL_321 = "mode_321"

    fun getSP(context: Context, name: String)
            = context.getSharedPreferences(name, Context.MODE_PRIVATE)


    fun getSPEditor(context: Context, name: String)
            = getSP(context, name).edit()
}