package com.example.locatracker

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import java.util.concurrent.TimeUnit

class distracker : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var distanceTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button

    private var previousLocation: Location? = null
    private var totalDistance: Float = 0f
    private var isTracking = false
    private var startTime: Long = 0
    private lateinit var handler: Handler

    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private val MIN_DISTANCE_THRESHOLD = 2 // Minimum distance in meters to consider as movement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distracker) // Ensure this matches your layout file name

        distanceTextView = findViewById(R.id.distance)
        timeTextView = findViewById(R.id.time)
        startButton = findViewById(R.id.start)
        stopButton = findViewById(R.id.stop)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        startButton.setOnClickListener {
            startTracking()
        }

        stopButton.setOnClickListener {
            stopTracking()
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            initializeLocationTracking()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                initializeLocationTracking()
            } else {
                // Permission denied, show a message to the user
            }
        }
    }

    private fun initializeLocationTracking() {
        val locationRequest = LocationRequest.create().apply {
            interval = 5000
            fastestInterval = 2000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                for (location in locationResult.locations) {
                    if (isTracking) {
                        updateLocation(location)
                    }
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun startTracking() {
        isTracking = true
        startTime = System.currentTimeMillis()
        totalDistance = 0f
        previousLocation = null

        handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                val elapsedTime = System.currentTimeMillis() - startTime
                timeTextView.text = "Time: ${TimeUnit.MILLISECONDS.toSeconds(elapsedTime)}s"
                handler.postDelayed(this, 1000)
            }
        })

        startButton.isEnabled = false
        stopButton.isEnabled = true
    }

    private fun stopTracking() {
        isTracking = false
        handler.removeCallbacksAndMessages(null)

        startButton.isEnabled = true
        stopButton.isEnabled = false
    }

    private fun updateLocation(location: Location) {
        if (previousLocation != null) {
            val distance = previousLocation!!.distanceTo(location)
            if (distance > MIN_DISTANCE_THRESHOLD) {
                totalDistance += distance
            }
        }
        previousLocation = location
        distanceTextView.text = "Distance: $totalDistance meters"
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}
