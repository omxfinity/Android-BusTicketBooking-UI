package com.naruto.voyagebus

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Reference to your bus icon
        val busIcon = findViewById<ImageView>(R.id.busIcon)

        // Fade-in + scale animation
        busIcon.alpha = 0f
        busIcon.scaleX = 0.8f
        busIcon.scaleY = 0.8f
        busIcon.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(1500)
            .start()

        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPrefs = getSharedPreferences("VoyageBusPrefs", MODE_PRIVATE)
            val isLoggedIn = sharedPrefs.getBoolean("is_logged_in", false)
            
            val intent = if (isLoggedIn) {
                Intent(this, HomeActivity::class.java)
            } else {
                Intent(this, RegisterActivity::class.java)
            }

            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 3000)
    }
}
