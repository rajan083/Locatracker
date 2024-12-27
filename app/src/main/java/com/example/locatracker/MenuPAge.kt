package com.example.locatracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MenuPAge : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_page)

        val alarm = findViewById<ImageView>(R.id.bt_alarm).setOnClickListener(){
            startActivity(Intent(this, Alarm::class.java))
        }
        val map = findViewById<ImageView>(R.id.bt_map).setOnClickListener(){
            startActivity(Intent(this, distracker::class.java))
        }
        val tracker = findViewById<ImageView>(R.id.bt_tracker).setOnClickListener(){
            startActivity(Intent(this, distracker::class.java))
        }
        val qrsbtn = findViewById<ImageView>(R.id.btnscan).setOnClickListener(){
            startActivity(Intent(this,QRScanner::class.java))
        }

    }
}