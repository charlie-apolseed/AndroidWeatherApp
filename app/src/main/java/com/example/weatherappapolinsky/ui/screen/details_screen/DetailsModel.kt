package com.example.weatherappapolinsky.ui.screen.details_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappapolinsky.data.WeatherResult
import com.example.weatherappapolinsky.network.WeatherAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailsModel @Inject constructor(
    val weatherAPI: WeatherAPI,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var city = savedStateHandle.get<String>("city")
    var detailsUiState: DetailsUiState by mutableStateOf(DetailsUiState.Loading)

    init {
        getWeather()
    }


    fun getWeather() {
        Log.d("DetailsModel", "getWeather() called")
        detailsUiState = DetailsUiState.Loading
        viewModelScope.launch {
            detailsUiState = try {
                val result = weatherAPI.getWeather(city!!)
                DetailsUiState.Success(result)
            } catch (e: IOException) {
                Log.d("DetailsModel", "IO Exception: {$e.message}")
                DetailsUiState.Error
            } catch (e: HttpException) {
                Log.d("DetailsModel", "HTTP Exception: {$e.message}")
                Log.d("DetailsModel", "HTTP Exception: ${e.code()}")
                if (e.code() == 404) {
                    DetailsUiState.LocationError
                } else {
                    DetailsUiState.Error
                }
            } catch (e: Exception) {
                Log.d("DetailsModel", "Other Exception: {$e.message}")
                DetailsUiState.Error
            }
        }
    }
}

sealed interface DetailsUiState {
    data class Success(val weatherResult: WeatherResult) : DetailsUiState
    object Error : DetailsUiState
    object Loading : DetailsUiState
    object LocationError : DetailsUiState
}