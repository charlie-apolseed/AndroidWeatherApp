package com.example.weatherappapolinsky.ui.screen.details_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherappapolinsky.data.WeatherResult


@Composable
fun DetailsScreen(
    detailsViewModel: DetailsModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = {
            detailsViewModel.getWeather()
        }) {
            Text(text = "Get Weather")
        }
        when (detailsViewModel.detailsUiState) {
            is DetailsUiState.Loading -> CircularProgressIndicator()
            is DetailsUiState.Success -> DetailsResultScreen((detailsViewModel.detailsUiState as DetailsUiState.Success).weatherResult)
            is DetailsUiState.Error -> Text(text = "Error...")
        }
    }

}

@Composable
fun DetailsResultScreen(weatherResult: WeatherResult) {
    Column() {
        Text(text = "Weather in ${weatherResult.name}")
        Text(text = "Temp: ${weatherResult.main?.temp}")
    }
}