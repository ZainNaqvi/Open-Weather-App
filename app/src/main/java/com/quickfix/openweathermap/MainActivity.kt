package com.quickfix.openweathermap

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import kotlin.math.ceil


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
        val apiKey = "45bb8427b1a339565af6ebe2468589d2"
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lng}&appid=${apiKey}"

       val jsonData = JsonObjectRequest(Request.Method.GET, url , null, { response ->
        setValues(response)
       }, {
           Toast.makeText(this, "Internal server error!", Toast.LENGTH_LONG).show()
       })

       queue.add(jsonData)
    }
  private fun  setValues(response: JSONObject){
        cityName.text=response.getString("name")
        var lat = response.getJSONObject("coord").getString("lat")
        var lng = response.getJSONObject("coord").getString("lon")
        coordinates.text="latitude - $lat, longitude - $lng"
        weather.text = response.getJSONArray("weather").getJSONObject(0).getString("main")
        var tempr = response.getJSONObject("main").getString("temp")
        tempr = (((tempr).toFloat()-273.15)).toInt().toString()
        tempText.text = tempr + "°C"
        var min_tempr = response.getJSONObject("main").getString("temp_min")
        min_tempr=(((min_tempr).toFloat()-273.15)).toInt().toString()
        minTemp.text = min_tempr+ "°C"
        var max_tempr = response.getJSONObject("main").getString("temp_max")
        max_tempr=((ceil((max_tempr).toFloat()-273.15))).toInt().toString()
        maxTemp.text = max_tempr+ "°C"
        pressure.text = response.getJSONObject("main").getString("pressure")
        humidity.text = response.getJSONObject("main").getString("humidity")
        windSpeed.text ="Wind Speed - " + response.getJSONObject("wind").getString("speed")
        degree.text ="Degree - " + response.getJSONObject("wind").getString("deg")
        gust.text ="Gust - " + response.getJSONObject("wind").getString("gust")
    }
}