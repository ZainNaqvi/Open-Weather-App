package com.quickfix.openweathermap

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices


class SplashActivity : AppCompatActivity() {
    lateinit var mfusedlocation:FusedLocationProviderClient
    private var MyRequestCode:Int = 101010
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mfusedlocation = LocationServices.getFusedLocationProviderClient(this)
        getLocation()
    }


    @SuppressLint("MissingPermission")
    private fun getLocation(){
        if(CheckPermission()){
            if (LocationEnabled()) {
                mfusedlocation.lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result
                if(location==null){
                    NewLocation()
                }else{
                    Log.d("Location",location.latitude.toString())
                    Handler(Looper.getMainLooper()).postDelayed({
                      var intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("lat", location.latitude.toString())
                        intent.putExtra("lng", location.longitude.toString())
                        startActivity(intent)
                        finish()
                    },2000)
                }
                }
            }
                else{
                 Toast.makeText(this,"Please turn onn your GPS location",Toast.LENGTH_LONG).show()
            }
        }
        else{
            RequestPermission()
        }
    }
    @SuppressLint("MissingPermission")
    private fun NewLocation() {
        var locationRequest = com.google.android.gms.location.LocationRequest()
        locationRequest.priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
//        locationRequest.numUpdates = 0
        mfusedlocation=LocationServices.getFusedLocationProviderClient(this)
        mfusedlocation.requestLocationUpdates(locationRequest,locationCallback,Looper.myLooper())
    }
    private val  locationCallback= object: LocationCallback(){
        override fun onLocationResult(p0:LocationResult){
            var lastLocation:Location = p0.lastLocation!!
        }
    }
    private fun LocationEnabled(): Boolean {
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return   locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)|| locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun CheckPermission(): Boolean {
     if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED
         ||ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
    return true
     }
        return  false
    }
    private fun RequestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),MyRequestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MyRequestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            } else {
                // Permission not granted, handle accordingly
                Toast.makeText(this, "Location permission not granted", Toast.LENGTH_LONG).show()

            }
        }
    }

}

