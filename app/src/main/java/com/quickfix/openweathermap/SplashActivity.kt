package com.quickfix.openweathermap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class SplashActivity : AppCompatActivity() {
    lateinit var mfusedlocation:FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mfusedlocation = LocationServices.getFusedLocationProviderClient(this)
    }
}