package com.example.weatherappapolinsky.ui.screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CitiesListModel @Inject constructor()
    : ViewModel() {
    var locations = mutableListOf<String>()

    fun addLocation(location: String) {
        locations.add(location)
    }

    fun deleteLocation(location: String) {
        locations.remove(location)
    }
}