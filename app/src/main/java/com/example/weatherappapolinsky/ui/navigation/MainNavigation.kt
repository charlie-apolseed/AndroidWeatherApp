package com.example.weatherappapolinsky.ui.navigation

sealed class Screen(val route: String) {
    object CitiesList : Screen("citiesList")
    object Details : Screen("details")
}