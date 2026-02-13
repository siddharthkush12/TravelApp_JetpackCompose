package com.example.travelapp.ui.home.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeTabScreen(
) {
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),

            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(30) {
                Text(
                    text = "Item $it",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = Color.Black
                )
            }
        }
    }
}