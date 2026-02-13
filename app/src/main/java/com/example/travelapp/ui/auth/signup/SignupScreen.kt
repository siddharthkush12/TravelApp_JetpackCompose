package com.example.travelapp.ui.auth.signup



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.travelapp.R
import com.example.travelapp.navigation.Home
import com.example.travelapp.navigation.Login
import com.example.travelapp.ui.components.AuthInputText
import com.example.travelapp.ui.components.BackRoundButton
import com.example.travelapp.ui.components.ErrorAlertDialog
import com.example.travelapp.ui.components.SocialMediaButton
import com.example.travelapp.ui.theme.TealCyan
import kotlinx.coroutines.flow.collectLatest


@Composable
fun SignUpScreen(navController: NavController, viewModel: SignupViewModel= hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val fullName=viewModel.fullName.collectAsStateWithLifecycle()
    val email=viewModel.email.collectAsStateWithLifecycle()
    val password=viewModel.password.collectAsStateWithLifecycle()

    when(uiState){
        is SignupViewModel.SignUpEvent.Error -> {
            ErrorAlertDialog(
                message = (uiState as SignupViewModel.SignUpEvent.Error).message,
                onDismiss = {
                    viewModel.onClearError()
                }
            )
        }
        else -> Unit
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collectLatest { events->
            when(events){
                is SignupViewModel.SignUpNavigation.NavigationToHome->{
                    navController.navigate(Home)
                }
                is SignupViewModel.SignUpNavigation.NavigationToLogin->{
                    navController.navigate(Login)
                }
            }
        }
    }


    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.signup)
    )

    val gradient = Brush.verticalGradient(
        colors = listOf(
            TealCyan.copy(0.3f), Color.White, TealCyan.copy(0.2f)
        )
    )
//    Button(
//        onClick = {
//            viewModel.onAlreadyHaveAccountButtonClick()
//        },
//        modifier = Modifier
//            .width(50.dp)
//            .zIndex(1f)
//            .padding(top = 30.dp, start = 10.dp),
//        colors = ButtonDefaults.buttonColors(Color.White.copy(alpha = 0.6f)),
//        shape = CircleShape,
//        contentPadding = PaddingValues(0.dp)
//    ) {
//        Image(
//            painter = painterResource(R.drawable.backarrow),
//            contentDescription = "back"
//        )
//    }
    BackRoundButton(
        onClick = { viewModel.onAlreadyHaveAccountButtonClick() }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.height(200.dp),

            )

        Spacer(modifier = Modifier.size(10.dp))

        Box(
            modifier = Modifier.shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(50.dp),
                ambientColor = TealCyan.copy(alpha = 0.3f),
                spotColor = TealCyan.copy(alpha = 0.5f)
            )
        ) {
            Card(
                modifier = Modifier.size(330.dp, 500.dp),
                shape = RoundedCornerShape(50.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.7f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp, 5.dp)
                ) {
                    Text(
                        text = "Signup",
                        fontSize = 30.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.ExtraLight,
                        color = TealCyan,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    AuthInputText(
                        value = fullName.value,
                        onValueChange = {viewModel.onNameChange(it) },
                        placeholder = "Name",
                        label = "Full Name",
                        keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.size(5.dp))

                    AuthInputText(
                        value = email.value,
                        onValueChange = {viewModel.onEmailChange(it)},
                        placeholder = "Email",
                        label = "Email",
                        keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    AuthInputText(
                        value = password.value,
                        onValueChange = {viewModel.onPasswordChange(it)},
                        placeholder = "Password",
                        label = "Password",
                        keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier
                    )

                    Spacer(modifier = Modifier.size(20.dp))


                    Button(
                        onClick = {viewModel.onSignUpButtonClick()},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(TealCyan)
                    ) {
                        Text(
                            text = "Sign Up",
                            fontSize = 20.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }

                    TextButton(
                        onClick = {viewModel.onAlreadyHaveAccountButtonClick()},
                    ) {
                        Text(
                            text = "Already have an account? Sign In",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = TealCyan
                        )
                    }

                    Spacer(modifier = Modifier.size(25.dp))

                    Text(
                        text = "or via social media",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.size(10.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SocialMediaButton(
                            text = "Facebook",
                            icon = R.drawable.ic_fb_icon,
                            Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        SocialMediaButton(
                            text = "Google",
                            icon = R.drawable.ic_google_icon,
                            Modifier.weight(1f)
                        )

                    }

                }
            }
        }

    }
}