package com.example.locatracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class alarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show()
    }
}
