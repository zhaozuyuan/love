package com.hanhanlove.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import java.util.*

/**
 * create by zuyuan on 2019/12/25
 */
class AnimationTextView : TextView {

    companion object {
        const val WANG_JIN_YAN_TTF_PATH = "font/wangjinyan.ttf"
    }

    private var mCurrentTimer: Timer?= null

    var completeText : String = ""

    var textRunning = false

    var textRunningListener: TextRunningListener?= null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        setTextStylePath(WANG_JIN_YAN_TTF_PATH)
    }

    fun setTextStylePath(path: String) {
        val face = Typeface.createFromAsset(resources.assets, path)
        typeface = face
    }

    /*慢慢将文字展示出来*/
    fun scheduleToShowText(text: String, period: Long, delay: Long = 0) {
        stopTextTask()
        completeText = text
        if (completeText.isEmpty()) {
            setText("")
            return
        }

        //启动任务
        textRunning = true
        textRunningListener?.stateChange(textRunning)
        mCurrentTimer = Timer()
        mCurrentTimer!!.schedule(object : TimerTask() {
            var index = 0
            override fun run() {
                this@AnimationTextView.post {
                    //去除所有的空格
                    while (index < completeText.length && completeText[index] == ' ') index++

                    if (index == completeText.length) {
                        cancel()
                    } else {
                        setText(completeText.substring(0, ++index))
                    }
                }
            }

            override fun cancel(): Boolean {
                textRunning = false
                textRunningListener?.stateChange(textRunning)
                return super.cancel()
            }
        }, delay, period)
    }

    fun stopTextAnimation(text: String = completeText) {
        stopTextTask()
        setText(text)
    }

    override fun onDetachedFromWindow() {
        mCurrentTimer?.cancel()
        super.onDetachedFromWindow()
    }

    private fun stopTextTask() {
        mCurrentTimer?.cancel()
    }

    interface TextRunningListener {
        fun stateChange(textRunning: Boolean)
    }
}