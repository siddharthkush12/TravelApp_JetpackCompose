package com.example.travelapp.ui.components



import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.travelapp.R
import com.example.travelapp.ui.theme.TealCyan


@Composable
fun ErrorAlertDialog(
    message: String,
    onDismiss: () -> Unit
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.questioning))


    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp),
        containerColor = Color.White,
        icon = {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(200.dp)
            )
        },

        title = {
            Text(
                text = "Something went wrong",
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
            )
        },
        text = {
            Text(
                text = message,
                fontSize = 16.sp,
                color = Color(0xFF555555),
                lineHeight = 20.sp
            )
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                modifier = Modifier.padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(TealCyan)
            ) {
                Text(
                    text = "OK",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    )
}


@Composable
fun CenterLoading(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}



@Composable
fun BackRoundButton(
    onClick:()->Unit
){
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(50.dp)
            .zIndex(1f)
            .padding(top = 30.dp, start = 10.dp),
        colors = ButtonDefaults.buttonColors(Color.White.copy(alpha = 0.6f)),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.backarrow),
            contentDescription = "back"
        )
    }
}



@Composable
fun BackButton(
    onClick:()->Unit
){
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(50.dp).padding(start = 10.dp),
        colors = ButtonDefaults.buttonColors(Color.White.copy(alpha = 0.6f)),
        shape = CircleShape,
        elevation = ButtonDefaults.buttonElevation(10.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.backarrow),
            contentDescription = "back"
        )
    }
}




@Composable
fun CustomEditText(
    modifier: Modifier = Modifier
) {

    var name by remember { mutableStateOf("Siddharth") }

    Box(
        modifier=modifier
    ) {
        Text(
            text = "Name",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier
                .padding(start = 16.dp, top = 12.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            singleLine = true,
            label = null
        )
    }
}


