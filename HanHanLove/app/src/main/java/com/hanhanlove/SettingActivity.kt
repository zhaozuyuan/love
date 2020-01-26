package com.hanhanlove

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hanhanlove.util.SPUtil
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private var select = 1

    private var ts = 1

    private var aopen321 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        rb1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rb2.isChecked = false
                rb3.isChecked = false
                select = 0
            }
        }

        rb2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rb1.isChecked = false
                rb3.isChecked = false
                select = 1
            }
        }

        rb3.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rb1.isChecked = false
                rb2.isChecked = false
                select = 2
            }
        }

        rb4.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rb5.isChecked = false
                rb6.isChecked = false
                ts = 0
            }
        }

        rb5.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rb4.isChecked = false
                rb6.isChecked = false
                ts = 1
            }
        }

        rb6.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rb4.isChecked = false
                rb5.isChecked = false
                ts = 2
            }
        }


        rb7.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rb8.isChecked = false
                aopen321 = true
            }
        }

        rb8.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rb7.isChecked = false
                aopen321 = false
            }
        }


        val v = SPUtil.getSP(baseContext, SPUtil.ALBUM_SP).getInt(SPUtil.MODEL_NAME, -1)
        if (v == -1) {
            SPUtil.getSPEditor(baseContext, SPUtil.ALBUM_SP).putInt(SPUtil.MODEL_NAME, 2).apply()
        } else {
            when(v) {
                0 -> rb1.isChecked = true
                1 -> rb2.isChecked = true
                2 -> rb3.isChecked = true
            }
        }

        val tv = SPUtil.getSP(baseContext, SPUtil.ALBUM_SP).getInt(SPUtil.MODEL_TEXT_NAME, -1)
        if (tv == -1) {
            SPUtil.getSPEditor(baseContext, SPUtil.ALBUM_SP).putInt(SPUtil.MODEL_TEXT_NAME, 1).apply()
        } else {
            when(tv) {
                0 -> rb4.isChecked = true
                1 -> rb5.isChecked = true
                2 -> rb6.isChecked = true
            }
        }

        if (SPUtil.getSP(baseContext, SPUtil.ALBUM_SP).getBoolean(SPUtil.MODEL_321, true)) {
            rb7.isChecked = true
        } else {
            rb8.isChecked = true
        }
    }

    override fun onPause() {
        SPUtil.getSPEditor(baseContext, SPUtil.ALBUM_SP)
            .putInt(SPUtil.MODEL_NAME, select)
            .putInt(SPUtil.MODEL_TEXT_NAME, ts)
            .putBoolean(SPUtil.MODEL_321, aopen321)
            .commit()
        super.onPause()
    }
}
