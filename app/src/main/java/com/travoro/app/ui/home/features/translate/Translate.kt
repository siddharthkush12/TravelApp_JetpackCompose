package com.travoro.app.ui.home.features.translate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.travoro.app.ui.components.CustomTopBar


@Composable
fun TranslateScreen(
    homeNavController: NavController
) {

    val dynamicColor = MaterialTheme.colorScheme.onSurface

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column() {
            CustomTopBar(
                title = "TRANSLATE",
                onBackClick = { homeNavController.popBackStack() }
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Coming Soon..."
                )
            }
        }

    }
}