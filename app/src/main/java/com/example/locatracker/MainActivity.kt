package com.example.locatracker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap
    private val REQUEST_LOCATION_PERMISSION = 123

    private lateinit var tvlatitude: TextView
    private lateinit var tvlongitude: TextView
    private lateinit var tvAddress: TextView
    private lateinit var bt_distancetracker: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tvlatitude = findViewById(R.id.tvlatitude)
        tvlongitude = findViewById(R.id.tvlongitude)
        tvAddress = findViewById(R.id.tvaddress)
        bt_distancetracker = findViewById(R.id.distancetracker)

        bt_distancetracker.setOnClickListener(){
            val intent = Intent(this, distracker::class.java)
            startActivity(intent)
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapfragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        requestLocationPermission()
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_LOCATION_PERMISSION)
        } else {
            getLastKnownLocation()
        }
    }

    private fun getLastKnownLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    updateUI(latitude, longitude)
                } else {
                    Toast.makeText(this, "Error fetching location", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUI(latitude: Double, longitude: Double) {
        tvlatitude.text = "Latitude: $latitude"
        tvlongitude.text = "Longitude: $longitude"

        val geocoder = Geocoder(this)
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses != null && addresses.isNotEmpty()) {
            val address = addresses[0].getAddressLine(0)
            tvAddress.text = "Address: $address"
        }
        val userLocation = LatLng(latitude, longitude)
        googleMap.addMarker(MarkerOptions().position(userLocation).title("Your Location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastKnownLocation()
            } else {
                Toast.makeText(this, "Some error occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
    }
}
