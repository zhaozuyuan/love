package com.hanhanlove.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.media.Image
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import java.util.*

/**
 * create by zuyuan on 2019/12/25
 *
 * 相册展示，只在精确值模式下运行
 */
class PhotoAlbum1 : LinearLayout, IPhotoAlbumXML {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    lateinit var photos : MutableList<Int>

    var onceWidth = 0

    var realWidth = 0

    /*初始化配置*/
    init {
        orientation = HORIZONTAL
    }

    override fun setPhotoAlbum(arrayId: Int) {
        photos = defaultGetPhotos(arrayId, resources)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var realWidthSpec = widthMeasureSpec
        val height = MeasureSpec.getSize(heightMeasureSpec)
        if (onceWidth == 0) {
            onceWidth = MeasureSpec.getSize(widthMeasureSpec)
        }
        super.onMeasure(realWidthSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onDetachedFromWindow() {
        handler.removeMessages(1)
        super.onDetachedFromWindow()
    }

    private fun bigger(iv: ImageView) {
        //相较于自身变大，从xy 0.5的地方缩放
        val biggerAnim = ScaleAnimation(1.0f, 1.15f, 1.0f, 1.15f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
            ScaleAnimation.RELATIVE_TO_SELF, 0.5f)
        biggerAnim.duration = 1600
        iv.startAnimation(biggerAnim)
    }
}