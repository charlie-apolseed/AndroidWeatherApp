package com.example.weatherappapolinsky.ui.screen.details_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.weatherappapolinsky.R
import com.example.weatherappapolinsky.data.WeatherResult
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    detailsViewModel: DetailsModel = hiltViewModel(),
    onBackAction: () -> Unit
) {
    var city by remember { mutableStateOf(detailsViewModel.city) }
    if (city == null) city = "Unknown" else {
        if (city!!.length > 20) city = city!!.substring(0, 17) + "..."
    }



    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(onClick = { onBackAction() }) {
                            Icon(
                                Icons.AutoMirrored.Rounded.ArrowBack,
                                tint = Color.White,
                                contentDescription = stringResource(R.string.back),
                                modifier = Modifier.size(50.dp)
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = stringResource(R.string.logo),
                                modifier = Modifier
                                    .size(50.dp)
                                    .padding(end = 10.dp)
                            )
                            Text(
                                text = stringResource(R.string.header_text),
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }

                        Spacer(modifier = Modifier.size(50.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            when (detailsViewModel.detailsUiState) {
                is DetailsUiState.Loading -> CircularProgressIndicator(modifier.padding(top = 50.dp))
                is DetailsUiState.Success -> DetailsResultScreen((detailsViewModel.detailsUiState as DetailsUiState.Success).weatherResult)
                is DetailsUiState.Error -> Column {
                    Text(
                        modifier = Modifier.padding(top = 20.dp),
                        text = stringResource(R.string.error),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Button(
                        modifier = Modifier.padding(top = 20.dp),
                        onClick = { detailsViewModel.getWeather() }
                    ) {
                        Text(stringResource(R.string.reload))
                    }
                }

                is DetailsUiState.LocationError -> Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = stringResource(R.string.location_not_found)
                )
            }
        }
    }
}

@Composable
fun DetailsResultScreen(weatherResult: WeatherResult) {
    Column(
        modifier = Modifier.fillMaxSize(.9f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Weather in ${weatherResult.name}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight(700)
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${weatherResult.weather?.get(0)?.main}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight(700)
                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            "https://openweathermap.org/img/w/${
                                weatherResult.weather?.get(0)?.icon
                            }.png"
                        )
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(180.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(20.dp))
                )
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Card(
                modifier = Modifier
                    .height(140.dp)
                    .width(190.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp, top = 20.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Temp: ${weatherResult.main?.temp?.toInt()}°C",
                        fontSize = 20.sp,
                        fontWeight = FontWeight(700)
                    )
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = "Feels Like: ${weatherResult.main?.feelsLike?.toInt()}°C",
                        fontSize = 20.sp,
                        fontWeight = FontWeight(500)
                    )
                    Text(
                        text = "Low: ${weatherResult.main?.tempMin?.toInt()}°C",
                        fontSize = 20.sp,
                        fontWeight = FontWeight(700)
                    )
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = "High: ${weatherResult.main?.tempMax?.toInt()}°C",
                        fontSize = 20.sp,
                        fontWeight = FontWeight(500)
                    )
                }
            }
            Card(
                modifier = Modifier
                    .height(140.dp)
                    .width(170.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp, top = 20.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Sunrise: ",
                        fontSize = 20.sp,
                        fontWeight = FontWeight(700)
                    )
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = formatTimestampToTime(weatherResult.sys!!.sunrise!!) + " am",
                        fontSize = 20.sp,
                        fontWeight = FontWeight(700)
                    )
                    Text(
                        text = "Sunset: ",
                        fontSize = 20.sp,
                        fontWeight = FontWeight(700)
                    )
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = formatTimestampToTime(weatherResult.sys!!.sunset!!) + " pm",
                        fontSize = 20.sp,
                        fontWeight = FontWeight(700)
                    )
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .fillMaxHeight()
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = "Wind Speed:               ${weatherResult.wind?.speed?.toInt()} kph",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700)
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                    text = "Wind Direction:          ${formatWindDir(weatherResult.wind?.deg!!)}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700)
                )
            }
        }
    }
}

fun formatTimestampToTime(timestamp: Long): String {
    // Convert the timestamp to milliseconds
    val date = Date(timestamp * 1000)

    // Define a SimpleDateFormat to extract the time in "h:mm" format
    val formatter = SimpleDateFormat("h:mm", Locale.getDefault())

    // Return the formatted time string
    return formatter.format(date)
}

fun formatWindDir(direction: Double): String {
    return when {
        direction in 0.0..22.5 || direction > 337.5 -> "North"
        direction in 22.5..67.5 -> "Northeast"
        direction in 67.5..112.5 -> "East"
        direction in 112.5..157.5 -> "Southeast"
        direction in 157.5..202.5 -> "South"
        direction in 202.5..247.5 -> "Southwest"
        direction in 247.5..292.5 -> "West"
        direction in 292.5..337.5 -> "Northwest"
        else -> "Invalid direction" // Edge case for invalid input
    }
}