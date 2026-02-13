//package com.example.travelapp
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import io.github.fletchmckee.liquid.LiquidState
//import io.github.fletchmckee.liquid.liquid
//import io.github.fletchmckee.liquid.rememberLiquidState
//
//
//@Preview(showSystemUi = true)
//@Composable
//fun LiquidScreen(
//    modifier: Modifier = Modifier,
//    liquidState: LiquidState = rememberLiquidState(),
//) {
//    Box(
//        modifier = modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//
//        Button(
//            onClick = { /* TODO */ },
//            modifier = Modifier.liquid(liquidState) {
//                frost = 10.dp
//                shape = RoundedCornerShape(25.dp)
//                refraction = 0.5f
//                curve = 0.5f
//                edge = 0.1f
//                tint = Color.White.copy(alpha = 0.2f)
//                saturation = 1.5f
//                dispersion = 0.25f
//            }
//        ) {
//            Text("Liquid Button")
//        }
//    }
//}