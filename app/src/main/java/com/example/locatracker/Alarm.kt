package com.example.locatracker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Alarm : AppCompatActivity() {

    private lateinit var timePicker: TimePicker
    private lateinit var setalarm: Button
    private lateinit var bt_map: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alarm)

        timePicker = findViewById(R.id.timepiker)
        setalarm = findViewById(R.id.setalarm)
        bt_map = findViewById(R.id.bt_map)

        bt_map.setOnClickListener(){
            val intent = Intent(this, distracker::class.java)
            startActivity(intent)
        }

        setalarm.setOnClickListener(){
            setAlarm()
        }
    }

    private fun setAlarm(){
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
        calendar.set(Calendar.MINUTE, timePicker.minute)
        calendar.set(Calendar.SECOND, 0)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, alarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this,0, intent, PendingIntent.FLAG_IMMUTABLE)

        alarmManager.setWindow(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, 300000L,pendingIntent)

        Toast.makeText(this,"Alarm set for ${timePicker.hour}:${timePicker.minute}", Toast.LENGTH_SHORT).show()

    }
}