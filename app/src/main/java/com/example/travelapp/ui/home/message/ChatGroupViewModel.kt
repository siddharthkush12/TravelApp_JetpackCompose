package com.example.travelapp.ui.home.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.core.network.ApiResult
import com.example.travelapp.core.network.safeApiCall
import com.example.travelapp.data.remote.api.TravelApiService
import com.example.travelapp.data.remote.dto.message.ChatGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatGroupViewModel @Inject constructor(
    private val travelApiService: TravelApiService
) : ViewModel() {

    private val _groups = MutableStateFlow<List<ChatGroup>>(emptyList())
    val groups = _groups.asStateFlow()

    private val _uiState = MutableStateFlow<ChatGroupEvent>(ChatGroupEvent.Idle)
    val uiState = _uiState.asStateFlow()


    init {
        fetchGroups()
    }

    fun fetchGroups() {

        viewModelScope.launch {

            _uiState.value = ChatGroupEvent.Loading

            val response = safeApiCall {
                travelApiService.getChatGroup()
            }
            when (response) {

                is ApiResult.Success -> {

                    _groups.value = response.data.data
                    _uiState.value = ChatGroupEvent.Success

                }

                is ApiResult.Error -> {

                    _uiState.value =
                        ChatGroupEvent.Error(response.message)

                }

                is ApiResult.Exception -> {

                    _uiState.value =
                        ChatGroupEvent.Error(response.message)

                }

            }

        }

    }

    fun deleteChatAndTrip(tripId: String){
        viewModelScope.launch {
            _uiState.value = ChatGroupEvent.Loading
            val response = safeApiCall {
                travelApiService.deleteTrip(tripId)
            }
            when (response) {
                is ApiResult.Success -> {
                    _groups.value=_groups.value.filterNot {
                        it.trip._id==tripId
                    }
                    _uiState.value = ChatGroupEvent.Success
                }
                is ApiResult.Error -> {
                    _uiState.value = ChatGroupEvent.Error(response.message)
                }
                is ApiResult.Exception -> {
                    _uiState.value = ChatGroupEvent.Error(response.message)
                }
            }
        }
    }


    sealed class ChatGroupEvent {
        object Idle : ChatGroupEvent()
        object Loading : ChatGroupEvent()
        object Success : ChatGroupEvent()
        data class Error(val message: String) : ChatGroupEvent()
    }

}