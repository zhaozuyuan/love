package com.hanhanlove

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hanhanlove.album.base.OnFinishCallback
import com.hanhanlove.databinding.ActivityPhotoBinding
import com.hanhanlove.util.AnimationUtil
import com.hanhanlove.util.LiveHandler
import com.hanhanlove.util.MusicBox
import com.hanhanlove.util.SPUtil
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_photo)

        when(SPUtil.getSP(baseContext, SPUtil.ALBUM_SP).getInt(SPUtil.MODEL_NAME, 2)) {
            0 -> {
                mBinding.paPhoto1.setPhotoAlbum(R.array.album1)
                tvTip.text = "来源于\"专集相册\""
            }
            1 -> {
                mBinding.paPhoto1.setPhotoAlbum(R.array.album2)
                tvTip.text = "来源于\"最新相册\""

            }
            2 -> {
                mBinding.paPhoto1.setPhotoAlbum(R.array.album3)
                tvTip.text = "来源于\"历史相册\""
            }

        }

        mBinding.paPhoto1.setOnFinishCallback(object : OnFinishCallback {
            override fun onFinish() {
                var player = openMusic()
                if (player == null) {
                    player = openMusic()
                }
                player?.setOnPreparedListener {
                    player?.start()
                    mBinding.paPhoto1.loopImage()
                }
            }

        })

        tvGoVoice.setOnClickListener {
            startActivity(Intent(baseContext, VoiceActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        
        val open321 = SPUtil.getSP(baseContext, SPUtil.ALBUM_SP).getBoolean(SPUtil.MODEL_321, true)
        val time = if (open321) 2550L else 600L
        LiveHandler.safePostDelay(lifecycle, Runnable {
            rotate()
            var player = openMusic()
            if (player == null) {
                player = openMusic()
            }
            player?.setOnPreparedListener {
                player?.start()
                mBinding.paPhoto1.loopImage()
            }
        }, time)

        if (open321) {
            tvStart.text = "3"
            tvStart.startAnimation(AnimationUtil.getBiggerAnim(900, 0.8F, 0.75F))
            tvStart.postDelayed({
                tvStart.text = "2"
                tvStart.startAnimation(AnimationUtil.getBiggerAnim(750, 0.8F, 0.75F))
            }, 910)
            tvStart.postDelayed({
                tvStart.text = "1"
                tvStart.startAnimation(AnimationUtil.getBiggerAnim(750, 0.8F, 0.75F))
            }, 1670)
            tvStart.postDelayed({
                tvStart.clearAnimation()
                tvStart.visibility = View.GONE
            }, 2400)
        }
    }

    override fun onStop() {
        MusicBox.closeMusic("music/background_1.mp3")
        ivMusic.clearAnimation()
        mBinding.paPhoto1.stopLoop()
        super.onStop()
    }

    private fun openMusic() =
        MusicBox.openAssetMusic("music/background_1.mp3", baseContext)

    private fun rotate() {
        val anim = RotateAnimation(0F, 360F, Animation.RELATIVE_TO_SELF,
            0.5F, Animation.RELATIVE_TO_SELF, 0.5F)
        anim.interpolator = LinearInterpolator()
        anim.duration = 8000
        anim.repeatCount = -1
        ivMusic.startAnimation(anim)
    }
}
