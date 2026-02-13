package com.example.travelapp.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.travelapp.R
import com.example.travelapp.di.Session
import com.example.travelapp.navigation.Home
import com.example.travelapp.navigation.Onboarding


@Composable
fun SplashScreen(session: Session, onNavigate: (Any) -> Unit, viewModel: SplashScreenViewModel = hiltViewModel()) {

    val navigate by viewModel.navigateNext.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.startSplash()
    }
    LaunchedEffect(navigate) {
        if (navigate) {
            if(session.getToken().isNullOrBlank()){
                onNavigate(Onboarding)
            }else{
                onNavigate(Home)
            }
        }
    }


    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.travoro)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            iterations = 1,
            modifier = Modifier.fillMaxSize()
        )

    }
}