package com.example.travelapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor() : ViewModel(){

    private val _navigateNext= MutableStateFlow(false)
    val navigateNext: StateFlow<Boolean> = _navigateNext

    fun startSplash(){
        viewModelScope.launch {
            delay(4000)
            _navigateNext.value=true
        }
    }
}

