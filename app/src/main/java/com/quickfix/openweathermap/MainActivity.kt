package com.quickfix.openweathermap

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val latitude:String  = intent.getStringExtra("lat").toString()
        val longitude:String  = intent.getStringExtra("lng").toString()

        getJsonData(latitude, longitude)
    }

    private fun getJsonData( lat:String, lng:String ){
        val queue = Volley.newRequestQueue(this)
        val apiKey = "e1816280a64ae2d04fc39692c5a87354"
        val url = "https://api.openweathermap.org/data/3.0/onecall?lat=${lat}&lon=${lng}&appid=${apiKey}"

       val jsonData = JsonObjectRequest(Request.Method.GET, url , null, { response ->
           Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()
       }, {
           Toast.makeText(this, "Internal server error!", Toast.LENGTH_LONG).show()
       })

       queue.add(jsonData)
    }
}