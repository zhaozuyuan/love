package com.hanhanlove.util

import android.view.animation.Animation
import android.view.animation.ScaleAnimation

object AnimationUtil {


    fun getBiggerAnim(duration: Long = 1600L, toX: Float = 1.14F, toY: Float = 1.14F) : Animation {
        //相较于自身变大，从xy 0.5的地方缩放
        val biggerAnim = ScaleAnimation(1.0f, toX, 1.0f, toY,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f)
        biggerAnim.duration = duration
        return biggerAnim
    }
}