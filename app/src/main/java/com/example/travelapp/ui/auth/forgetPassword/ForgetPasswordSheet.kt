package com.example.travelapp.ui.auth.forgetPassword


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.travelapp.R
import com.example.travelapp.ui.auth.login.LoginViewModel
import com.example.travelapp.ui.components.AuthInputText
import com.example.travelapp.ui.theme.TealCyan
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswordSheet(
    viewModel: LoginViewModel,
    onDismiss: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    val forgotState by viewModel.forgotState.collectAsStateWithLifecycle()

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.email))


    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(
            topStart = 25.dp, topEnd = 25.dp
        )
    ) {

        if(forgotState is LoginViewModel.ForgotPasswordState.Success) {
            Box(
                modifier=Modifier
                    .fillMaxWidth()
                    .height(360.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LottieAnimation(
                        composition = composition,
                        iterations = 1,
                        modifier = Modifier.size(180.dp)
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    Text(
                        text = "Please check your email",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        textAlign = TextAlign.Center

                    )
                }
            }
            LaunchedEffect(Unit) {
                delay(5000)
                viewModel.clearForgetPasswordState()
                onDismiss()
            }

            return@ModalBottomSheet
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Forgot Password",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enter your email to receive a reset link",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(20.dp))

            AuthInputText(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                keyboardOption = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                placeholder = "Email",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.onForgetPasswordButtonClick(email)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(TealCyan),
                enabled = forgotState !is LoginViewModel.ForgotPasswordState.Loading
            ) {
                if (forgotState is LoginViewModel.ForgotPasswordState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Send Reset Link")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (forgotState) {
                is LoginViewModel.ForgotPasswordState.Error -> {
                    Text(
                        text = (forgotState as LoginViewModel.ForgotPasswordState.Error).message,
                        color = Color.Red
                    )
                }
                else -> Unit
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

    }

}