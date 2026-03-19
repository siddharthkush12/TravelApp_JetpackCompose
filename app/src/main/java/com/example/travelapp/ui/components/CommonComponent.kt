package com.example.travelapp.ui.components



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
            .width(50.dp)
            .padding(start = 10.dp),
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
    modifier: Modifier,
    label: @Composable () -> Unit,
    value:String,
    onValueChange:(String)->Unit,
    enabled:Boolean=true
) {


    Box(
        modifier= modifier
    ) {


        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            singleLine = true,
            label =  label ,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Gray,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = TealCyan,
                focusedLabelColor = TealCyan,
            ),
            enabled=enabled
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderDropdown(
    modifier: Modifier,
    selectedGender:String,
    onGenderChange:(String)->Unit
){
    val options=listOf("male","female","other")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded=expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ){
        OutlinedTextField(
            value = selectedGender.replaceFirstChar { it.uppercase() },
            onValueChange = {},
            readOnly = true,
            label={Text("Gender")},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TealCyan,
                focusedBorderColor = TealCyan,
                focusedLabelColor = TealCyan
            ),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier=Modifier.height(150.dp).background(TealCyan.copy(alpha = 0.1f)),

        ){
            options.forEach{gender->
                DropdownMenuItem(
                    text={ Text(gender.replaceFirstChar { it.uppercase() }) },
                    onClick = {
                        onGenderChange(gender)
                        expanded=false
                    }
                )
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerField(
    modifier: Modifier=Modifier,
    selectedDate:String,
    onDateSelected:(String)->Unit
){

    var showPicker by remember { mutableStateOf(false) }
    val datePickerState= rememberDatePickerState()



    if(showPicker){
        DatePickerDialog (
            onDismissRequest = { showPicker=false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {millis->
                            val formatted=java.text.SimpleDateFormat(
                                "dd-MM-yyyy",
                                java.util.Locale.getDefault()
                            ).format(java.util.Date(millis))
                            onDateSelected(formatted)
                        }
                        showPicker=false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton ={
                TextButton(
                    onClick = { showPicker=false }
                ) {
                    Text("Cancel")
                }
            }
        ){
            DatePicker(state=datePickerState)
        }
    }
    OutlinedTextField(
        value = selectedDate,
        onValueChange = {},
        readOnly = true,
        label = { Text("DOB") },
        modifier = modifier
            .fillMaxWidth(),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date Picker",
                modifier = Modifier.clickable {
                    showPicker = true
                }
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TealCyan,
            focusedBorderColor = TealCyan,
            focusedLabelColor = TealCyan,
            focusedTrailingIconColor = TealCyan
        )
    )
}



@Composable
fun CustomTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackClick: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        TealCyan,
                        TealCyan.copy(alpha = 0.5f)
                    )
                )
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .padding(top = 30.dp)
    ) {


        Box(
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterStart)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f))
                .clickable { onBackClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = Color.White
            )
        }

        Text(
            text = title,
            fontSize = 25.sp,
            letterSpacing = 1.sp,
            fontFamily = FontFamily(Font(R.font.inter)),
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}