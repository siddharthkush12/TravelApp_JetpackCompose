package com.example.travelapp.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelapp.R
import com.example.travelapp.data.remote.dto.onboarding.OnboardingPage
import com.example.travelapp.ui.theme.TealCyan
import kotlinx.coroutines.launch


@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit = {}
) {

    val onboardingPages = listOf(
        OnboardingPage(
            title = "Explore the World",
            description = "Discover amazing destinations and plans your perfect trip with ease.",
            image = R.drawable.first
        ),
        OnboardingPage(
            title = "Book with Confidence",
            description = "Find the best hotels and flights at the lowest prices, anytime, anywhere.",
            image = R.drawable.second
        ),
        OnboardingPage(
            title = "Travel Made Simple",
            description = "Manage your bookings, track flights, and enjoy a seamless travel experience.",
            image = R.drawable.third
        )
    )

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        HorizontalPager(
            state = pagerState,
        ) { page ->
            val isLastPage = page == onboardingPages.lastIndex

            OnboardingPageUI(
                onboardingPage = onboardingPages[page],
                buttonText = if (pagerState.currentPage == onboardingPages.lastIndex) "Get Started" else "Next",
                onButtonClick = {
                    if (!isLastPage) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page + 1)
                        }
                    } else {
                        onFinishOnboarding()
                    }
                }
            )
        }
    }
}

@Composable
fun OnboardingPageUI(
    onboardingPage: OnboardingPage,
    buttonText: String,
    onButtonClick: () -> Unit = {}
) {

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color.Black.copy(alpha = 0.3f)
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = onboardingPage.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
        )


        Column(
            modifier = Modifier
                .size(width = 400.dp, height = 245.dp)
                .align(Alignment.BottomCenter)
                .padding(24.dp)
                .background(
                    Color.White.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 15.dp, vertical = 5.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = onboardingPage.title,
                fontSize = 25.sp,
                fontFamily = FontFamily(Font(R.font.inknutantiquamedium)),
            )
            Text(
                text = onboardingPage.description,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.size(7.dp))
            Button(
                onClick = onButtonClick,
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(TealCyan)
            ) {
                Text(text = buttonText)
            }
        }
    }
}