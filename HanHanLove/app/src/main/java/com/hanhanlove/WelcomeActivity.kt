package com.hanhanlove

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.core.app.ActivityCompat
import com.hanhanlove.util.LiveHandler
import com.hanhanlove.util.SPUtil
import com.hanhanlove.widget.AnimationTextView
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.android.synthetic.main.debug_activity.*
import java.security.Permissions

class WelcomeActivity : AppCompatActivity() {

    private val mDearString = "To 最爱的小仙女"

    private val mTextString = "你准备好了吗？"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        tvWelcomeOk.setOnClickListener {
            startActivity(Intent(baseContext, PhotoActivity::class.java))
        }

        ivSetting.setOnClickListener {
            startActivity(Intent(baseContext, SettingActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    private fun init() {
        tvWelcomeGuide.text = ""
        tvWelcomeText.text = ""
        tvWelcomeOk.visibility = View.INVISIBLE

        when(SPUtil.getSP(baseContext, SPUtil.ALBUM_SP)
            .getInt(SPUtil.MODEL_TEXT_NAME, 1)){
            0 -> {
                tvWelcomeGuide.postDelayed({
                    tvWelcomeGuide.scheduleToShowText(mDearString, 95)
                }, 700)

                tvWelcomeGuide.textRunningListener = object : AnimationTextView.TextRunningListener {
                    override fun stateChange(textRunning: Boolean) {
                        if (!textRunning) {
                            tvWelcomeText.scheduleToShowText(mTextString, 95, 180)
                        }
                    }
                }

                tvWelcomeText.textRunningListener = object : AnimationTextView.TextRunningListener {
                    override fun stateChange(textRunning: Boolean) {
                        if (!textRunning) {
                            val anim = AlphaAnimation(0F, 1F)
                            anim.duration = 400
                            LiveHandler.safePostDelay(lifecycle, Runnable {
                                tvWelcomeOk.startAnimation(anim)
                                tvWelcomeOk.visibility = View.VISIBLE
                            }, 400)
                        }
                    }
                }
            }
            1 -> {
                tvWelcomeGuide.postDelayed({
                    tvWelcomeGuide.scheduleToShowText(mDearString, 170)
                }, 800)

                tvWelcomeGuide.textRunningListener = object : AnimationTextView.TextRunningListener {
                    override fun stateChange(textRunning: Boolean) {
                        if (!textRunning) {
                            tvWelcomeText.scheduleToShowText(mTextString, 170, 300)
                        }
                    }
                }

                tvWelcomeText.textRunningListener = object : AnimationTextView.TextRunningListener {
                    override fun stateChange(textRunning: Boolean) {
                        if (!textRunning) {
                            val anim = AlphaAnimation(0F, 1F)
                            anim.duration = 800
                            LiveHandler.safePostDelay(lifecycle, Runnable {
                                tvWelcomeOk.startAnimation(anim)
                                tvWelcomeOk.visibility = View.VISIBLE
                            }, 800)
                        }
                    }
                }
            }
            else -> {
                tvWelcomeGuide.text = mDearString
                tvWelcomeText.text = mTextString
                tvWelcomeOk.visibility = View.VISIBLE
                -1L
            }

        }
    }
}
