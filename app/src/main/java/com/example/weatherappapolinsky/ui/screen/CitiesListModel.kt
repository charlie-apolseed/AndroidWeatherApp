package com.example.weatherappapolinsky.ui.screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CitiesListModel @Inject constructor() : ViewModel() {
    // Backing property to encapsulate the mutable state
    private val _locations = MutableStateFlow<List<String>>(emptyList())
    val locations: StateFlow<List<String>> = _locations

    fun addLocation(location: String) {
        _locations.value += location
    }

    fun deleteLocation(location: String) {
        _locations.value -= location
    }
}