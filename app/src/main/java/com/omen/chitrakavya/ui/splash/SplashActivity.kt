package com.omen.chitrakavya.ui.splash


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.omen.chitrakavya.R
import android.content.Intent
import android.net.Uri
import android.widget.VideoView

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val videoView = findViewById<VideoView>(R.id.videoView)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.splash_video
        val uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)

        videoView.setOnCompletionListener { mp ->
            // Video playback completed, start the next activity
            // For example, you can launch your main activity here
            startActivity(Intent(this@SplashActivity, com.omen.chitrakavya.MainActivity::class.java))
            finish() // Finish the splash activity
        }

        videoView.start() // Start playing the video
    }
}