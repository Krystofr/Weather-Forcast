package com.christopher.weather_forcast.api

import android.util.Log
import androidx.lifecycle.LiveData
import com.christopher.weather_forcast.model.current_weather.CurrentWeather
import com.christopher.weather_forcast.model.weather_forecast.ForecastWeather
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object RetrofitRepository {

    var job1: CompletableJob? = null
    var job2: CompletableJob? = null

    //getting current weather function
    fun getCurrentWeather(
        location: String
    ): LiveData<CurrentWeather> {
        Log.d("GetCurrentWeather", "getting weather")
        job1 = Job()
        return object : LiveData<CurrentWeather>() {
            override fun onActive() { //when this method is called do something
                super.onActive()
                job1?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {//get current weather on the background thread
                        try {
                            val currentWeather: CurrentWeather =
                                RetrofitBuilder.apiService.getCurrentWeather(location)
                            withContext(Main) {
                                //set value on the main thread
                                value = currentWeather
                                Log.d("Method -current weather", currentWeather.toString())
                                theJob.complete()
                            }
                        } catch (e: Throwable) {
                            Log.d("Network-Error", "Network Error")
                        }
                    }
                }
            }
        }
    }

    fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): LiveData<CurrentWeather> {
        Log.d("GetCurrentWeather 2", "getting weather")
        job1 = Job()
        return object : LiveData<CurrentWeather>() {
            override fun onActive() { //when this method is called do something
                super.onActive()
                job1?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {//get current weather on the background thread
                        try {
                            val currentWeather: CurrentWeather =
                                RetrofitBuilder.apiService.getCurrentWeather(latitude, longitude)
                            withContext(Main) {
                                //set value on the main thread
                                value = currentWeather
                                Log.d("Method-current weather2", currentWeather.toString())
                                theJob.complete()
                            }
                        } catch (e: Throwable) {

                        }

                    }
                }
            }
        }
    }

    //getting forecast weather functions
    fun getWeatherForecast(
        location: String
    ): LiveData<ForecastWeather> {
        Log.d("GetForecastWeather", "getting forecast weather")
        job2 = Job()
        return object : LiveData<ForecastWeather>() {
            override fun onActive() { //when this method is called do something
                super.onActive()
                job1?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {//get forecast weather on the background thread
                        try {
                            val forecastWeather: ForecastWeather =
                                RetrofitBuilder.apiService.getWeatherForecast(location)
                            withContext(Main) {
                                //set value on the main thread
                                value = forecastWeather
                                Log.d("Method-Forecast Weather", forecastWeather.toString())
                                theJob.complete()
                            }
                        } catch (e: Throwable) {
                            Log.d("Network-Error", "Network Error")
                        }

                    }
                }
            }
        }
    }

    fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ): LiveData<ForecastWeather> {
        Log.d("GetForecastWeather2", "getting forecast weather")
        job2 = Job()
        return object : LiveData<ForecastWeather>() {
            override fun onActive() { //when this method is called do something
                super.onActive()
                job1?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {//get forecast weather on the background thread
                        try {
                            val forecastWeather: ForecastWeather =
                                RetrofitBuilder.apiService.getWeatherForecast(latitude, longitude)
                            withContext(Main) {
                                //set value on the main thread
                                value = forecastWeather
                                Log.d("Method-ForecastWeather2", forecastWeather.toString())
                                theJob.complete()
                            }
                        } catch (e: Throwable) {

                        }
                    }
                }
            }
        }
    }

    fun cancelJobs() {
        job1?.cancel()
        job2?.cancel()
    }
}