package com.example.travelapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.travelapp.di.Session
import com.example.travelapp.ui.auth.login.LoginScreen
import com.example.travelapp.ui.auth.signup.SignUpScreen
import com.example.travelapp.ui.home.HomeScreen
import com.example.travelapp.ui.onboarding.OnboardingScreen
import com.example.travelapp.ui.splash.SplashScreen


@Composable
fun NavGraph(navController: NavHostController, session: Session) {

    val startPoint=if(session.getToken().isNullOrBlank()) SplashScreen else Home

    NavHost(
        navController = navController,
        startDestination = SplashScreen
    ) {
        composable<SplashScreen> {
            SplashScreen(
                session = session,
                onNavigate = { destination->
                    navController.navigate(destination) {
                        popUpTo(SplashScreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Onboarding> {
            OnboardingScreen(
                onFinishOnboarding = {
                    navController.navigate(Login) {
                        popUpTo(Onboarding) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Login> {
            LoginScreen(navController)
        }

        composable<SignUp> {
            SignUpScreen(navController)
        }

        composable<Home>{
            HomeScreen()
        }

    }

}