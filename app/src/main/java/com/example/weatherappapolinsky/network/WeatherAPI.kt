package com.example.weatherappapolinsky.network

import com.example.weatherappapolinsky.data.WeatherResult
import retrofit2.http.GET
import retrofit2.http.Path

//API URL: https://api.openweathermap.org/data/2.5/weather?q=Budapest,hu&units=metric&appid=af202cc79994974075dc39a172a340c9
//Host: https://api.openweathermap.org
//Path: data/2.5/weather?q=Budapest,hu&units=metric&appid=af202cc79994974075dc39a172a340c9

interface WeatherAPI {
    @GET("data/2.5/weather?q={city}&units=metric&appid=af202cc79994974075dc39a172a340c9")
    suspend fun getWeather(
        @Path("city") city: String
    ): WeatherResult
}