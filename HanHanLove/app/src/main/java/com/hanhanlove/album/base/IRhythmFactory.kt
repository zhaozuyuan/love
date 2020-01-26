package com.hanhanlove.album.base

import android.view.animation.Animation

interface IRhythmFactory {

    fun getAnimationList(): List<AnimationBean>

    /*动画种子*/
    data class AnimationBean(var duration: Long,
                             var anim: Animation?,
                             var hasAnimation: Boolean = true,
                             var isMove : Boolean = false,
                             var interpolatorInt: Int = 0) //匀速 加速 减速 这里以后再修改，受不了了
}