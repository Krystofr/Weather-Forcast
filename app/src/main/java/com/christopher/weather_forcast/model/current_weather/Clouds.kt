package com.christopher.weather_forcast.model.current_weather


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)