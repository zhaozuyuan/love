package com.hanhanlove.debug

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hanhanlove.PhotoActivity
import com.hanhanlove.R
import com.hanhanlove.WelcomeActivity
import com.hanhanlove.util.LiveHandler
import kotlinx.android.synthetic.main.debug_activity.*

class DebugActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.debug_activity)

        val face = Typeface.createFromAsset(assets, "font/wangjinyan.ttf")
        tvHello.typeface = face

        btnDebugWelcome.setOnClickListener {
            startActivity(Intent(baseContext, WelcomeActivity::class.java))
        }

        btnDebugPhoto.setOnClickListener {
            startActivity(Intent(baseContext, PhotoActivity::class.java))
        }
    }
}
