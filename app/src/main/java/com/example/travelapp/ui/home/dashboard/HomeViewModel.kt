package com.example.travelapp.ui.home.dashboard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.core.network.ApiResult
import com.example.travelapp.core.network.safeApiCall
import com.example.travelapp.data.remote.api.TravelApiService
import com.example.travelapp.ui.utils.LocationHelper
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val travelApiService: TravelApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeEvent>(HomeEvent.Idle)
    val uiState = _uiState.asStateFlow()
    private val locationHelper = LocationHelper(context)
    private val _location = MutableStateFlow<Pair<Double, Double>?>(null)
    val location = _location.asStateFlow()
    private val _city = MutableStateFlow<String?>(null)
    val city = _city.asStateFlow()

    private val _weather=MutableStateFlow<WeatherUiModel?>(null)
    val weather=_weather.asStateFlow()


    fun fetchLocationAndCity() {
        viewModelScope.launch {
            _uiState.value=HomeEvent.Loading

            val loc = locationHelper.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY)
            if(loc==null){
                _uiState.value=HomeEvent.Error("Unable to fetch location")
                return@launch
            }
            val latitude = loc.latitude
            val longitude = loc.longitude

            _location.value = Pair(latitude, longitude)

            val cityName = locationHelper.getCityFromLocation(context,latitude, longitude)
            _city.value = cityName

            val weatherResponse= safeApiCall {
                travelApiService.getWeather(
                    latitude = loc.latitude,
                    longitude = loc.longitude
                )
            }
            when(weatherResponse){
                is ApiResult.Success->{

                    val current=weatherResponse.data.data
                    if(current!=null){
                        val description=getWeatherDescription(current.weathercode)
                        _weather.value= WeatherUiModel(
                            temperature = current.temperature,
                            weatherCode = current.weathercode,
                            isDay = current.is_day,
                            windSpeed = current.windspeed,
                            description = description
                        )
                        _uiState.value=HomeEvent.Success
                    }
                }
                is ApiResult.Error->{
                    _uiState.value=HomeEvent.Error(weatherResponse.message)
                }
                is ApiResult.Exception->{
                    _uiState.value=HomeEvent.Error(weatherResponse.message)
                }
            }
        }
    }


    private fun getWeatherDescription(code: Int): String {
        return when (code) {
            0 -> "Clear Sky"
            1, 2, 3 -> "Partly Cloudy"
            45, 48 -> "Fog"
            51, 53, 55 -> "Drizzle"
            61, 63, 65, 80 -> "Rain"
            71, 73, 75 -> "Snow"
            95 -> "Thunderstorm"
            else -> "Unknown"
        }
    }


    fun clearError() {
        _uiState.value = HomeEvent.Idle
    }


    data class WeatherUiModel(
        val temperature: Double,
        val weatherCode: Int,
        val isDay: Int,
        val windSpeed: Double,
        val description:String
    )

    sealed class HomeEvent {
        object Idle : HomeEvent()
        object Loading : HomeEvent()
        object Success : HomeEvent()

        data class Error(val message: String) : HomeEvent()
    }


}