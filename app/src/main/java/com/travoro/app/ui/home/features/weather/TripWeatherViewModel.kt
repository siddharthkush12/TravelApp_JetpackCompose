package com.travoro.app.ui.home.features.weather


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travoro.app.core.network.ApiResult
import com.travoro.app.data.remote.dto.weather.AlertUiModel
import com.travoro.app.data.remote.dto.weather.ForecastUiModel
import com.travoro.app.data.remote.dto.weather.WeatherUiModel
import com.travoro.app.data.remote.repository.WeatherRepository
import com.travoro.app.ui.utils.formatDayOfWeek
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripWeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherEvent>(WeatherEvent.Idle)
    val uiState = _uiState.asStateFlow()


    fun fetchWeather(city: String) {
        viewModelScope.launch {
            _uiState.value = WeatherEvent.Loading

            when (val response = weatherRepository.getWeather(city)) {
                is ApiResult.Success -> {
                    val data = response.data.data
                    val today = data.forecast.first()

                    val uiModel = WeatherUiModel(
                        city = data.location,
                        todayTemp = today.avgTemp,
                        todayCondition = today.condition,
                        todayIcon = "https:${today.icon}",
                        todayHumidity = today.humidity,
                        todayWindSpeed = today.windSpeed,
                        todayUv = today.uv,
                        forecast = data.forecast.map {
                            ForecastUiModel(
                                date = formatDayOfWeek(it.date), // <--- Converts "2026-03-25" to "WED"
                                avgTemp = it.avgTemp,
                                minTemp = it.minTemp,
                                maxTemp = it.maxTemp,
                                condition = it.condition,
                                icon = "https:${it.icon}",
                                rainChance = it.rainChance,
                                uv = it.uv
                            )
                        },
                        alerts = data.alerts.map {
                            AlertUiModel(
                                headline = it.headline,       // Fixed naming
                                severity = it.severity,
                                urgency = it.urgency,         // Added from JSON
                                areas = it.areas,             // Added from JSON
                                category = it.category,       // Added from JSON
                                effective = it.effective,     // Fixed naming
                                expires = it.expires,         // Fixed naming
                                description = it.description,
                                instruction = it.instruction
                            )
                        }
                    )
                    _uiState.value = WeatherEvent.Success(uiModel)
                }

                is ApiResult.Error -> {
                    _uiState.value = WeatherEvent.Error("Error fetching weather")
                }

                is ApiResult.Exception -> {
                    _uiState.value = WeatherEvent.Error("Exception fetching weather")
                }
            }
        }
    }

    fun clearWeatherError() {
        _uiState.value = WeatherEvent.Idle
    }




    sealed class WeatherEvent {
        object Idle : WeatherEvent()
        object Loading : WeatherEvent()
        data class Success(val weather: WeatherUiModel) : WeatherEvent()
        data class Error(val message: String) : WeatherEvent()
    }
}