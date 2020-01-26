package com.hanhanlove.album.a1

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.Scroller
import com.hanhanlove.album.base.IPhotoAlbum
import com.hanhanlove.album.base.IPhotoAlbumXML
import com.hanhanlove.album.base.OnFinishCallback
import com.hanhanlove.album.base.glideNormalLoadImage


/**
 * create by zuyuan on 2019/12/25
 *
 * 相册展示1，只在精确值模式下运行
 *
 * img1 img2 img3 img4 img5
 * 缓存5个ImageView
 */
class PhotoAlbum1 : ViewGroup, IPhotoAlbumXML,
    IPhotoAlbum {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    companion object {

        /*默认子view数*/
        const val DEFAULT_CACHE_VIEWS = 5

        private const val WHAT = 0
    }

    //一张图片的宽度
    var onceWidth = 0

    //是否能够加载子view
    var canLoadChild = true

    //当前view下标
    var currentIndex = 0

    //图片容器
    lateinit var photos: MutableList<Int>

    //循环播放的下标
    private var loopIndex = 0

    //图片张数
    private var imgsLength = 0

    private var canRun = false

    //离起点的左侧距离
    private var leftOffset = 0

    private lateinit var scroller: Scroller

    private var isScroller = false

    private var isLastOne = false

    private var finishCallback: OnFinishCallback ?= null

    //完整的节奏动画
    private val anims = RhythmFactory1().getAnimationList()

    //匀速
    private val linearScroller = Scroller(context, LinearInterpolator())
    //加速
    private val accelerateScroller = Scroller(context, AccelerateInterpolator())
    //减速
    private val decelerateScroller = Scroller(context, DecelerateInterpolator())

    private val offsetHeight = resources.getDimension(
        resources.getIdentifier("status_bar_height", "dimen", "android"))
            .toInt()

    private val uiHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == WHAT && canRun) {
                nextImage()
            }
        }
    }

    override fun setPhotoAlbum(arrayId: Int) {
        photos = defaultGetPhotos(arrayId, resources)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (onceWidth == 0) {
            onceWidth = MeasureSpec.getSize(widthMeasureSpec)
        }
        //添加子view
        if (onceWidth != 0 && photos.size != imgsLength
            && visibility != GONE && canLoadChild ) {
            imgsLength = photos.size

            //移除所有原view
            if (childCount != 0) {
                removeAllViews()

            }

            for (i in 0 until DEFAULT_CACHE_VIEWS) {
                val iv = createImageView(onceWidth, LayoutParams.MATCH_PARENT)
                addView(iv)
                iv.measure(widthMeasureSpec, heightMeasureSpec)
            }

            //第一次加载
            if (childCount != 0) {
                firstLoadImage()
            }
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(
            leftOffset + onceWidth * DEFAULT_CACHE_VIEWS,
            MeasureSpec.EXACTLY), heightMeasureSpec)
    }

    override fun loopImage() {
        isScroller = false
        isLastOne = false
        canRun = true
        loopIndex = 0
        uiHandler.sendEmptyMessage(WHAT)
    }

    override fun stopLoop() {
        canRun = false
        uiHandler.removeMessages(WHAT)
    }

    override fun onDetachedFromWindow() {
        stopLoop()
        super.onDetachedFromWindow()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var start = l + leftOffset
        for (i in 0 until childCount) {
            val v = getChildAt(i)
            v.layout(start, t + offsetHeight, start + onceWidth,
                t + v.measuredHeight)
            start += onceWidth
        }
    }

    override fun computeScroll() {
        if (isScroller && scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, 0)
            invalidate()
        }
        if (isScroller && scroller.isFinished) {
            if (loopIndex >= 1) {
                leftOffset += onceWidth
            }
            onNextFinish()
        }
    }

    override fun nextImage() {
        isScroller = false
        beforeNext()

        //获取当前view
        val targetOne = getChildAt(currentIndex)
        if (targetOne == null) {
            afterNext()
            return
        }

        val i = loopIndex % anims.size
        val animBean = if (loopIndex > 0 && i == anims.size - 1) {
            isLastOne = true
            anims[i]
        } else {
            isLastOne = false
            anims[i]
        }
        //有动画
        if (animBean.hasAnimation) {
            postDelayed({ onNextFinish() }, animBean.duration)
            targetOne.startAnimation(animBean.anim)
        }
        //有移动
        if (animBean.isMove) {
            scroller = when (animBean.interpolatorInt) {
                0 -> {
                    linearScroller
                }
                1 -> {
                    accelerateScroller
                }
                else -> {
                    decelerateScroller
                }
            }
            scroller.startScroll(scrollX, 0, onceWidth, 0, animBean.duration.toInt())
            isScroller = true
            invalidate()
        }
    }

    override fun setOnFinishCallback(callback: OnFinishCallback) {
        this.finishCallback = callback
    }

    private fun createImageView(width : Int, height: Int) = ImageView(context).apply {
        scaleType = ImageView.ScaleType.FIT_CENTER
        layoutParams = LayoutParams(width, height)
    }

    private fun firstLoadImage() {
        val iv0 = getChildAt(0) as ImageView
        val iv1 = getChildAt(1) as ImageView

        iv0.glideNormalLoadImage(photos[0])
        iv1.glideNormalLoadImage(photos[1 % imgsLength])
    }

    /*当加载结束时*/
    private fun onNextFinish() {
        val iv = getChildAt(currentIndex) ?: return
        iv.clearAnimation()


        loopIndex++
        currentIndex++

        afterNext()
    }

    /*下一次加载开始前，需要把图片填充到+2的view位置*/
    private fun beforeNext() {
        val iv = getChildAt(currentIndex + 2) ?: return

        iv as ImageView
        iv.glideNormalLoadImage(photos[(loopIndex + 2) % imgsLength])
    }

    /*下一次加载结束后，需要将第一个ImageView移动到最后一个位置*/
    private fun afterNext() {
        if (currentIndex == 1) {
            isScroller = false

            val iv = getChildAt(0) ?: return

            currentIndex--
            removeView(iv)
            addView(iv)
        } else {
            if (!isScroller) {
                scrollBy(onceWidth, 0)
            }
            isScroller = false
        }


        if (canRun) {
            if (isLastOne) {
                finishCallback?.onFinish()
            } else {
                uiHandler.sendEmptyMessage(WHAT)
            }
        }
    }
}

