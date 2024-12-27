package com.example.locatracker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView

class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splashscreen)

        val img = findViewById<ImageView>(R.id.img_logo)
        val text = findViewById<TextView>(R.id.txt_name)
        val top_animation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val bottom_animation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation)

        img.startAnimation(top_animation)
        text.startAnimation(bottom_animation)

        Handler().postDelayed({
            startActivity(Intent(this, login::class.java))
            finish()
        },1500)
    }
}