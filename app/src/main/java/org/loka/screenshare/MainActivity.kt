package org.loka.screenshare

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import org.loka.screensharekit.EncodeBuilder
import org.loka.screensharekit.ScreenShareKit
import org.loka.screensharekit.callback.AudioCallBack
import org.loka.screensharekit.callback.H264CallBack
import org.loka.screensharekit.callback.RGBACallBack
import java.nio.ByteBuffer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.start).setOnClickListener {
            requestCapture()
        }

        findViewById<Button>(R.id.stop).setOnClickListener {
            ScreenShareKit.stop()
        }
    }


    private fun requestCapture() {

        ScreenShareKit.init(this)
            .config(screenDataType = EncodeBuilder.SCREEN_DATA_TYPE.H264, audioCapture = false)
            .onH264 { buffer, isKeyFrame, width, height, ts ->
                // h264 data
            }.onRGBA { rgba, width, height, stride, rotation, rotationChanged ->

            }.onStart {
                // toast
                Toast.makeText(this, "start", Toast.LENGTH_SHORT).show()
            }.onError { err ->
                // toast
                Toast.makeText(this, err.message, Toast.LENGTH_SHORT).show()
            }.start()
    }
}