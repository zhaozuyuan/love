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
import com.hanhanlove.widget.AnimationTextView
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.android.synthetic.main.debug_activity.*
import java.security.Permissions

class WelcomeActivity : AppCompatActivity() {

    private val mDearString = "To 可爱的小圆脸"

    private val mTextString = "你准备好了吗？"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

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
                    anim.duration = 1000
                    LiveHandler.getInstance().safePostDelay(lifecycle, Runnable {
                        tvWelcomeOk.startAnimation(anim)
                        tvWelcomeOk.visibility = View.VISIBLE
                    }, 1000)
                }
            }
        }

        tvWelcomeOk.setOnClickListener {
            startActivity(Intent(baseContext, PhotoActivity::class.java))
        }

        requestPermission()
    }


    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, Array(2){
            Manifest.permission.RECORD_AUDIO
        }, 1)
    }
}
