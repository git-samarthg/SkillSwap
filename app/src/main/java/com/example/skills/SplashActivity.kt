package com.example.skills

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.skills.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable fullscreen mode
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide() // Hides the action bar if present

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videoView = binding.videoView
        val videoPath = "android.resource://" + packageName + "/" + R.raw.sample_video
        val uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)

        // Listen for the media to be ready to set the correct scaling
        videoView.setOnPreparedListener { mediaPlayer ->
            val videoWidth = mediaPlayer.videoWidth
            val videoHeight = mediaPlayer.videoHeight

            val screenWidth = resources.displayMetrics.widthPixels
            val screenHeight = resources.displayMetrics.heightPixels

            val videoProportion = videoWidth.toFloat() / videoHeight.toFloat()
            val screenProportion = screenWidth.toFloat() / screenHeight.toFloat()

            // Adjust the video view size to ensure it fills the screen
            if (videoProportion > screenProportion) {
                videoView.layoutParams.width = screenWidth
                videoView.layoutParams.height = (screenWidth / videoProportion).toInt()
            } else {
                videoView.layoutParams.width = (screenHeight * videoProportion).toInt()
                videoView.layoutParams.height = screenHeight
            }

            videoView.start() // Start video playback
        }

        // Redirect to SignInActivity when the video finishes or on click
        videoView.setOnCompletionListener {
            navigateToSignIn()
        }

        videoView.setOnClickListener {
            navigateToSignIn()
        }
    }

    private fun navigateToSignIn() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish() // Ensure splash screen is not shown again on back press
    }
}
