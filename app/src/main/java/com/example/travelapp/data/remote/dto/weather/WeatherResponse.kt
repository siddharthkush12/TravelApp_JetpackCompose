package com.example.travelapp.data.remote.dto.weather

data class WeatherResponse(
    val success: Boolean,
    val data:CurrentWeather
)

data class CurrentWeather(
    val temperature:Double,
    val weathercode:Int,
    val is_day:Int,
    val windspeed:Double
)