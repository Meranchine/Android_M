package com.example.networkweather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.networkweather.weather.Forecast
import com.example.networkweather.weather.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    val baseURL ="http://t.weather.itboy.net/api/weather/city/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val cityCode = intent.getStringExtra("city_code")
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(baseURL+cityCode,{

            val gson =Gson()
            val WeatherType=object :TypeToken<Weather>(){}.type
            val weather=gson.fromJson<Weather>(it,WeatherType)
            textView_city.text=weather.cityInfo.city
            textView_province.text=weather.cityInfo.parent
            textView_temperature.text=weather.data.wendu
            textView_humidity.text=weather.data.shidu
            val firstday=weather.data.forecast.first()
            when(firstday.type){
                "晴"->imageView.setImageResource(R.drawable.sun)
                "阴"->imageView.setImageResource(R.drawable.cloud)
                "多云"->imageView.setImageResource(R.drawable.mcloud)
                "正雨"->imageView.setImageResource(R.drawable.rain)
                else->imageView.setImageResource(R.drawable.thunder)
            }

            val adapter= ArrayAdapter<Forecast>(this,android.R.layout.simple_list_item_1,weather.data.forecast)
            listview.adapter=adapter


            Log.d("Main2Acitivity","${weather.cityInfo.city} ${weather.cityInfo.parent}")
        },{
            Log.d("Main2Acitivity","$it")
        })
        queue.add(stringRequest)
    }
}