package com.example.weatherappapolinsky.ui.screen.details_screen

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

    fun getWeather() {
        detailsUiState = DetailsUiState.Loading
        viewModelScope.launch {
            detailsUiState = try {
                val result = weatherAPI.getWeather(city!!)
                DetailsUiState.Success(result)
            } catch (e: IOException) {
                DetailsUiState.Error
            } catch (e: HttpException) {
                DetailsUiState.Error
            } catch (e: Exception) {
                DetailsUiState.Error
            }
        }
    }
}

sealed interface DetailsUiState {
    data class Success(val weatherResult: WeatherResult) : DetailsUiState
    object Error : DetailsUiState
    object Loading : DetailsUiState
}