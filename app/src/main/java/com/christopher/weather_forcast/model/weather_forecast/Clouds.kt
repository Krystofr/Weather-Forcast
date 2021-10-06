package com.christopher.weather_forcast.model.weather_forecast


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)