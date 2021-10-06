package com.christopher.weather_forcast.model.current_weather


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    val h: Double
)