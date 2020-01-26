package com.hanhanlove

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.hanhanlove.util.LiveHandler
import com.hanhanlove.util.MusicBox
import kotlinx.android.synthetic.main.activity_photo.*
import kotlinx.android.synthetic.main.activity_voice.*
import kotlinx.android.synthetic.main.activity_voice.ivMusic

class VoiceActivity : AppCompatActivity() {

    private val str1 = StringBuilder()
        .append("\t\t\t\t")
        .append("亲爱的，我之前说：我会把你想要的都给你补上。现在我补上了第一个，一共3466行代码，造就了这个可爱的小玩意儿～")
        .append("\n\n")
        .toString()

    private val str2 = StringBuilder()
        .append("\t\t\t\t")
        .append("亲爱的，初次见面的时候，没想到我会这样爱你，现在会这么想念你。")
        .append("在北京，我几乎时时刻刻都在想你，想你的小感冒好了吗，想你最近是不是又不开心，想我们的牵手，想我们的余生。")
        .append("\n\n")
        .toString()

    private val str3 = StringBuilder()
        .append("\t\t\t\t")
        .append("亲爱的，我确实不是一个称职的男友，在你不开心的时候经常照顾不好你，还经常会惹你生气。")
        .append("很多时候我也知道自己说错话了，想要哄你，但却担心自己的糟糕表现会让你更伤心，我总想着等你气消一点再找你说话。")
        .append("我真的好怕哪一次你生气之后就再也不理我。")
        .append("\n\n")
        .toString()

    private val str4 = StringBuilder()
        .append("\t\t\t\t")
        .append("亲爱的，我爱你，很爱很爱你，我知道以后日子还长，但是我会努力让你每一天都能感受到我对你的爱！")
        .append("\n\n")
        .toString()

    private val str5 = StringBuilder()
        .append("\t\t\t\t")
        .append("亲爱的，往后余生，还请多多指教～")
        .append("\n")
        .toString()

    private var allStr = str1 + str2 + str3 + str4 + str5
    private val maxIndex = allStr.length - 1
    private var nextIndex = 0

    private var plyer : MediaPlayer ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice)

        tvText.let {
            val face = Typeface.createFromAsset(resources.assets, "font/wangjinyan.ttf")
            it.typeface = face
        }

        LiveHandler.safePostDelay(lifecycle, Runnable {
            s()
        }, 1000)

        tvPause.setOnClickListener {
            if (!pause) {
                tvPause.text = "恢  复"
                pause()
            } else {
                tvPause.text = "暂  停"
                restart()
            }
        }
    }

    private fun s() {
        plyer = MusicBox.openAssetMusic("music/voice_2.mp3", baseContext)
        if (plyer == null) {
            plyer = MusicBox.openAssetMusic("music/voice_2.mp3", baseContext)
        }

        plyer?.setOnPreparedListener {
            plyer?.start()
            nextTextWithDelay()
            rotate()
        }
    }

    override fun onStop() {
        super.onStop()
        pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        plyer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
    }

    private val whatNext = 1

    private var pause = false

    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            if (pause) return

            when(msg.what) {
                whatNext -> {
                    tvText.text = allStr.substring(0, msg.arg1)
                    nextTextWithDelay()
                }
                else -> {}
            }
        }
    }

    private fun pause() {
        pause = true
        ivMusic.clearAnimation()
        plyer?.let {
            if (it.isPlaying) {
                it.pause()
            }
        }
    }

    private fun restart() {
        pause = false
        plyer?.let {
            if (!it.isPlaying) {
                it.start()
                nextText()
                rotate()
            }
        }
        if (plyer == null || !plyer!!.isPlaying) {
            nextIndex = 1
            s()
        }
    }

    private fun nextText() {
        handler.sendEmptyMessage(whatNext)
    }

    private var nCount = 0
    private fun nextTextWithDelay() {
        var delay = 235L
        while (nextIndex < maxIndex) {
            val c = allStr[nextIndex]
            if (c == '\n') {
                nCount++
                delay = 250L
                if (nCount  == 6) {
                    delay = 500L
                } else if (nCount == 8) {
                    delay = 1100L
                }
                nextIndex++
            } else if (c == '\t' || c == ' ') {
                nextIndex++
            } else {
                break
            }
        }
        if (nextIndex != maxIndex) {
            nextIndex++

            val msg = Message.obtain()
            msg.what = whatNext
            msg.arg1 = nextIndex
            handler.sendMessageDelayed(msg, delay)
        }
    }

    private fun rotate() {
        val anim = RotateAnimation(0F, 360F, Animation.RELATIVE_TO_SELF,
            0.5F, Animation.RELATIVE_TO_SELF, 0.5F)
        anim.interpolator = LinearInterpolator()
        anim.duration = 8000
        anim.repeatCount = -1
        ivMusic.startAnimation(anim)
    }
}
