package com.example.weatherappapolinsky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherappapolinsky.ui.navigation.Screen
import com.example.weatherappapolinsky.ui.screen.CitiesListScreen
import com.example.weatherappapolinsky.ui.screen.details_screen.DetailsScreen
import com.example.weatherappapolinsky.ui.theme.WeatherAppApolinskyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppApolinskyTheme {
                NavGraph()
            }
        }
    }
}

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.CitiesList.route
    ) {
        composable(Screen.CitiesList.route) {
            CitiesListScreen(
                onNavigateToDetails = { city: String ->
                    navController.navigate("DetailsScreen?city=$city")
                }
            )
        }
        composable("DetailsScreen?city={city}") { city ->
            DetailsScreen(onBackAction = {
                navController.popBackStack()
            })
        }

    }
}