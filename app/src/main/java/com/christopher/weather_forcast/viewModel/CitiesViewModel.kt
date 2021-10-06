package com.christopher.weather_forcast.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.christopher.weather_forcast.database.CitiesRepository
import com.christopher.weather_forcast.model.City

class CitiesViewModel(application: Application) : AndroidViewModel(application) {

    // initialise the repository
    private var repository: CitiesRepository = CitiesRepository(application)

    //get all cities from database
    fun getCities() = repository.getCities()

    //add a city to the database
    fun addCity(city: City) {
        repository.addCity(city)
    }

    //delete a city from database
    fun deleteCity(city: City) {
        repository.deleteCity(city)
    }

}