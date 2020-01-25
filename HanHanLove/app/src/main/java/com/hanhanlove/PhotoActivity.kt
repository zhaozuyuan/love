package com.hanhanlove

import android.media.audiofx.Visualizer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hanhanlove.databinding.ActivityPhotoBinding
import com.hanhanlove.util.MusicBox

class PhotoActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_photo)


        mBinding.paPhoto1.post {
            mBinding.paPhoto1.simpleLoopImage()
        }

        openMusic()

        //MusicBox.openAssetMusic("music/background_1.mp3", baseContext)
    }

    override fun onStop() {
        MusicBox.closeMusic("music/background_1.mp3")
        super.onStop()
    }

    private fun openMusic() {
        MusicBox.openAssetMusicWithCaptureListener("music/background_1.mp3", baseContext,
            object : Visualizer.OnDataCaptureListener {
                override fun onFftDataCapture(
                    visualizer: Visualizer?,
                    fft: ByteArray?,
                    samplingRate: Int
                ) {
//                    fft!!
//                    val newData = ByteArray(fft.size)
//                    var abs: Byte
//                    for (i in fft.indices) {
//                        abs = kotlin.math.abs(fft[i].toInt()).toByte()
//                        //描述：Math.abs -128时越界
//                        newData[i] = if (abs < 0) 127 else abs
//                    }
//
//                    val sb = StringBuilder()
//                    for (i in newData.indices) {
//                        sb.append(newData[i].toInt()).append("-")
//                    }
                    //Log.d("TAGG", "--->$sb")

                }

                override fun onWaveFormDataCapture(
                    visualizer: Visualizer?,
                    waveform: ByteArray?,
                    samplingRate: Int
                ) { }
            })
    }
}
