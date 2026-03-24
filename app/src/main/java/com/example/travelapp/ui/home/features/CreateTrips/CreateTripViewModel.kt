package com.example.travelapp.ui.home.features.CreateTrips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.core.network.ApiResult
import com.example.travelapp.core.network.safeApiCall
import com.example.travelapp.data.remote.api.TravelApiService
import com.example.travelapp.data.remote.dto.trips.CreateTripRequest
import com.example.travelapp.data.remote.dto.trips.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTripViewModel @Inject constructor(
    val travelApiService: TravelApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow<CreateTripEvent>(CreateTripEvent.Nothing)
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<CreateTripNavigation>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _destination = MutableStateFlow("")
    val destination = _destination.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _memberPhone = MutableStateFlow("")
    val memberPhone = _memberPhone.asStateFlow()

    private val _tripType = MutableStateFlow("friends")
    val tripType = _tripType.asStateFlow()

    private val _travelStyle = MutableStateFlow("adventure")
    val travelStyle = _travelStyle.asStateFlow()

    private val _tripBudget = MutableStateFlow("")
    val tripBudget = _tripBudget.asStateFlow()



    private val _members = MutableStateFlow<List<Profile>>(emptyList())
    val members = _members.asStateFlow()



    fun onTitleChange(value: String) {
        _title.value = value
    }

    fun onDestinationChange(value: String) {
        _destination.value = value
    }

    fun onDescriptionChange(value: String) {
        _description.value = value
    }

    fun onMemberPhoneChange(value: String) {
        _memberPhone.value = value
    }

    fun onBudgetChange(value:Int){
        _tripBudget.value=value.toString()
    }

    fun onTripTypeChange(value: String) {
        _tripType.value = value
    }

    fun onTravelStyleChange(value: String) {
        _travelStyle.value = value
    }

    fun searchMember() {

        if (_memberPhone.value.isBlank()) {
            _uiState.value = CreateTripEvent.Error("Enter phone number")
            return
        }

        viewModelScope.launch {

            val response = safeApiCall {
                travelApiService.searchProfileByPhone(_memberPhone.value)
            }

            when (response) {

                is ApiResult.Success -> {

                    val profile = response.data.data

                    // avoid duplicates
                    if (!_members.value.any { it.userId == profile.userId }) {

                        _members.value = _members.value + profile

                    }

                    _memberPhone.value = ""

                }

                is ApiResult.Error -> {

                    _uiState.value =
                        CreateTripEvent.Error(response.message)

                }

                is ApiResult.Exception -> {

                    _uiState.value =
                        CreateTripEvent.Error(response.message)

                }

            }

        }

    }


    // REMOVE MEMBER

    fun removeMember(profile: Profile) {

        _members.value = _members.value.filterNot {
            it.userId == profile.userId
        }

    }


    // CREATE TRIP

    fun createTrip() {

        if (_title.value.isBlank()) {

            _uiState.value =
                CreateTripEvent.Error("Trip title required")

            return
        }

        viewModelScope.launch {
            _uiState.emit(CreateTripEvent.Loading)
            val response = safeApiCall {
                travelApiService.createTrip(
                    CreateTripRequest(
                        title = _title.value,
                        description = _description.value,
                        destination = _destination.value,
                        startDate = null,
                        endDate = null,
                        tripType = _tripType.value,
                        travelStyle = _travelStyle.value,
                        budget = _tripBudget.value.toIntOrNull(),
                        members = _members.value.map { it.userId }
                    )
                )
            }

            when (response) {

                is ApiResult.Success -> {
                    _uiState.value = CreateTripEvent.Success
                    _navigationEvent.emit(
                        CreateTripNavigation.NavigateBack
                    )

                }

                is ApiResult.Error -> {
                    _uiState.emit(
                        CreateTripEvent.Error(response.message)
                    )
                }

                is ApiResult.Exception -> {
                    _uiState.emit(
                        CreateTripEvent.Error(response.message)
                    )
                }
            }
        }
    }


    fun clearError() {
        _uiState.value = CreateTripEvent.Nothing
    }


    sealed class CreateTripNavigation {
        object NavigateBack : CreateTripNavigation()
    }


    sealed class CreateTripEvent {

        object Nothing : CreateTripEvent()
        object Success : CreateTripEvent()
        data class Error(val message: String) : CreateTripEvent()
        object Loading : CreateTripEvent()

    }
}