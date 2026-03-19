package com.example.travelapp.ui.home.Features.AddMembers

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.core.network.ApiResult
import com.example.travelapp.core.network.safeApiCall
import com.example.travelapp.data.remote.api.TravelApiService
import com.example.travelapp.data.remote.dto.travelAi.AddMemberRequest
import com.example.travelapp.data.remote.dto.trips.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddMembersViewModel @Inject constructor(
    private val travelApiService: TravelApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddMembersEvent>(AddMembersEvent.Idle)
    val uiState = _uiState.asStateFlow()

    private val _navigation = MutableSharedFlow<AddMembersNavigation>()
    val navigation = _navigation.asSharedFlow()


    private val _memberPhone = MutableStateFlow("")
    val memberPhone = _memberPhone.asStateFlow()

    private val _members = MutableStateFlow<List<Profile>>(emptyList())
    val members = _members.asStateFlow()



    fun onPhoneChange(value: String) {
        _memberPhone.value = value
    }

    fun searchMember() {
        viewModelScope.launch {
            val response = safeApiCall {
                travelApiService.searchProfileByPhone(_memberPhone.value)
            }
            when (response) {
                is ApiResult.Success -> {
                    val profile = response.data.data
                    if (!_members.value.any { it.userId == profile.userId }) {
                        _members.value = _members.value + profile
                    }
                    _memberPhone.value = ""
                }
                else -> {}
            }
        }
    }

    fun addMembersToTrip(tripId:String){
        if (_members.value.isEmpty()) {

            _uiState.value =
                AddMembersEvent.Error("Add at least one member")

            return
        }
        Log.d("AddMembers", "Members = ${_members.value}")
        viewModelScope.launch {
            val response = safeApiCall {
                travelApiService.addMembers(
                    tripId,
                    AddMemberRequest(
                        memberIds = _members.value.map { it.userId }
                    )
                )
            }
            when (response) {
                is ApiResult.Success -> {
                    _uiState.value= AddMembersEvent.Success
                    _navigation.emit(AddMembersNavigation.NavigateToMyTrip)
                }
                is ApiResult.Error -> {
                    _uiState.value = AddMembersEvent.Error(response.message)
                }
                is ApiResult.Exception -> {
                    _uiState.value = AddMembersEvent.Error(response.message)
                }
            }
        }
    }

    fun removeMember(profile: Profile) {
        _members.value = _members.value.filterNot {
            it.userId == profile.userId
        }
    }



    sealed class AddMembersEvent {
        object Idle : AddMembersEvent()
        object Loading : AddMembersEvent()
        object Success : AddMembersEvent()
        data class Error(val message: String) : AddMembersEvent()
    }

    sealed class AddMembersNavigation {
        object NavigateToMyTrip : AddMembersNavigation()
    }



}