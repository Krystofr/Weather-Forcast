package com.christopher.weather_forcast

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.christopher.weather_forcast.model.UserLocation
import com.christopher.weather_forcast.viewModel.WeatherViewModel
import com.google.android.gms.location.*
import com.google.android.libraries.places.api.Places

class MainActivity : AppCompatActivity() {
//Ohayō is a minimalistic weather forecast application developed using Kotlin language
    private val PERMISSION_ID = 42

    private lateinit var viewModel: WeatherViewModel

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize google places api
        if (!Places.isInitialized())
            Places.initialize(applicationContext, getString(R.string.api_key))

        //subscribe to Weather View model
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        //check if location permission is granted
        if (ContextCompat.checkSelfPermission(
                applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_ID
            )
        } else {
            getCurrentLocation()
        }

    }

    private fun getCurrentLocation() {

        val locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 3000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        LocationServices.getFusedLocationProviderClient(this@MainActivity)
            .requestLocationUpdates(
                locationRequest,
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult?) {
                        super.onLocationResult(locationResult)
                        LocationServices.getFusedLocationProviderClient(this@MainActivity)
                            .removeLocationUpdates(this)
                        if (locationResult != null && locationResult.locations.size > 0) {

                            val latestLocationIndex = locationResult.locations.size - 1
                            val latitude = locationResult.locations[latestLocationIndex].latitude
                            val longitude = locationResult.locations[latestLocationIndex].longitude

                            //query weather information for location
                            viewModel.setLocation(UserLocation(latitude, longitude))

                        }
                    }
                },
                Looper.getMainLooper()
            )

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
