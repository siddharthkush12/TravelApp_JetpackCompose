package com.example.travelapp.ui.home.TravelAI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.core.network.ApiResult
import com.example.travelapp.core.network.safeApiCall
import com.example.travelapp.data.remote.api.TravelApiService
import com.example.travelapp.data.remote.dto.travelAi.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TravelAiViewModel @Inject constructor(
    private val travelApi: TravelApiService
) : ViewModel() {

    /* ---------------- UI STATE ---------------- */

    private val _uiState = MutableStateFlow<TravelAiEvent>(TravelAiEvent.Idle)
    val uiState = _uiState.asStateFlow()

    private val _messages = MutableStateFlow<List<AiChatMessage>>(emptyList())
    val messages = _messages.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<TravelAiNavigation>()
    val navigationEvent = _navigationEvent.asSharedFlow()


    /* ---------------- CHAT STATE ---------------- */

    private var step = 0

    private var budget = ""
    private var days = ""
    private var travelStyle = ""
    private var groupType = ""
    private var currentCity = ""


    init {
        startConversation()
    }

    /* ---------------- START CHAT ---------------- */

    private fun startConversation() {
        step = 0
        _messages.value = listOf(
            AiChatMessage(text = "Hi 👋 I'm your AI travel planner"),
            AiChatMessage(
                text = "What is your trip budget?",
                options = listOf("5000", "10000", "50000", "150000")
            )
        )
    }


    /* ---------------- USER OPTION CLICK ---------------- */

    fun onOptionSelected(option: String) {
        when(option){
            "Restart Trip" -> {
                resetFlow()
                return
            }
            "Regenerate Trip" -> {
                generateTrip()
                return
            }
        }
        val list = _messages.value.toMutableList()
        list.add(
            AiChatMessage(
                text = option,
                isUser = true
            )
        )
        when (step) {
            0 -> {
                budget = option
                step = 1
                list.add(
                    AiChatMessage(
                        text = "How many days?",
                        options = listOf("2", "3", "5", "7", "10")
                    )
                )
            }
            1 -> {
                days = option
                step = 2
                list.add(
                    AiChatMessage(
                        text = "What travel style do you prefer?",
                        options = listOf(
                            "adventure",
                            "relax",
                            "luxury",
                            "budget",
                            "nature",
                            "spiritual"
                        )
                    )
                )
            }
            2 -> {
                travelStyle = option
                step = 3
                list.add(
                    AiChatMessage(
                        text = "Who are you travelling with?",
                        options = listOf(
                            "solo",
                            "couple",
                            "friends",
                            "family"
                        )
                    )
                )
            }
            3 -> {
                groupType = option
                step = 4
                list.add(
                    AiChatMessage(
                        text = "What is your starting city?",
                        options = listOf(
                            "bangalore",
                            "delhi",
                            "mumbai",
                            "lucknow"
                        )
                    )
                )
            }
            4 -> {
                currentCity = option
                _messages.value = list
                generateTrip()
                return
            }
        }

        _messages.value = list
    }


    /* ---------------- GENERATE AI TRIP ---------------- */

    private fun generateTrip() {

        viewModelScope.launch {

            _uiState.value = TravelAiEvent.Loading

            val loadingList = _messages.value.toMutableList()

            loadingList.add(
                AiChatMessage(text = "🤖 Generating your trip...")
            )

            _messages.value = loadingList

            val response = safeApiCall {

                travelApi.generateAiTrip(
                    AiTripRequest(
                        budget = budget.toInt(),
                        days = days.toInt(),
                        travelStyle = travelStyle,
                        groupType = groupType,
                        currentCity = currentCity
                    )
                )
            }

            when (response) {

                is ApiResult.Success -> {

                    val list = _messages.value.toMutableList()

                    list.add(
                        AiChatMessage(
                            tripResult = response.data.data
                        )
                    )

                    list.add(
                        AiChatMessage(
                            text = "Would you like another plan?",
                            options = listOf(
                                "Regenerate Trip",
                                "Restart Trip"
                            )
                        )
                    )

                    _messages.value = list
                    _uiState.value = TravelAiEvent.Success
                }

                is ApiResult.Error -> {
                    resetFlowWithError(response.message)
                }

                is ApiResult.Exception -> {
                    resetFlowWithError(response.message)
                }
            }
        }
    }


    /* ---------------- ACCEPT TRIP ---------------- */

    fun acceptTrip(tripData: AiTripData) {
        viewModelScope.launch {
            _uiState.value = TravelAiEvent.Loading

            val response = safeApiCall {
                travelApi.acceptTrip(
                    AcceptTripRequest(data = tripData)
                )
            }

            when (response) {

                is ApiResult.Success -> {
                    val list = _messages.value.toMutableList()
                    val tripId = response.data.data.id

                    list.add(
                        AiChatMessage(
                            text = "✅ Trip saved successfully!"
                        )
                    )

                    _messages.value = list
                    _uiState.value = TravelAiEvent.Success
                    _navigationEvent.emit(
                        TravelAiNavigation.NavigateToAddMembers(tripId)
                    )
                }

                is ApiResult.Error -> {
                    _uiState.value = TravelAiEvent.Error(response.message)
                }

                is ApiResult.Exception -> {
                    _uiState.value = TravelAiEvent.Error(response.message)
                }
            }
        }
    }


    /* ---------------- RESET FLOW ---------------- */

    private fun resetFlow() {

        step = 0
        budget = ""
        days = ""
        travelStyle = ""
        groupType = ""
        currentCity = ""

        startConversation()
    }

    private fun resetFlowWithError(message: String) {

        step = 0

        _messages.value = listOf(
            AiChatMessage(text = "⚠ $message"),
            AiChatMessage(
                text = "Let's try again. What is your trip budget?",
                options = listOf("5000", "10000", "50000", "150000")
            )
        )
    }


    /* ---------------- EVENTS ---------------- */

    sealed class TravelAiNavigation {
        data class NavigateToAddMembers(val tripId: String) : TravelAiNavigation()
    }

    sealed class TravelAiEvent {
        object Idle : TravelAiEvent()
        object Loading : TravelAiEvent()
        object Success : TravelAiEvent()
        data class Error(val message: String) : TravelAiEvent()
    }
}