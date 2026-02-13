package com.example.travelapp.ui.components



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelapp.R
import com.example.travelapp.ui.theme.TealCyan


@Composable
fun AuthInputText(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    label: String,
    keyboardOption: KeyboardOptions,
    modifier: Modifier

) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = TealCyan,
            focusedLabelColor = TealCyan,
            cursorColor = TealCyan,
            unfocusedBorderColor = Color.Gray,
            unfocusedLabelColor = Color.Gray,
        ),
        keyboardOptions = keyboardOption,
        modifier=modifier
    )

}

@Composable
fun SocialMediaButton(
    text:String,
    icon:Int,
    modifier: Modifier= Modifier
) {
    Button(
        onClick = {},
        modifier = modifier,
        contentPadding = PaddingValues(0.dp,8.dp),
        colors= ButtonDefaults.buttonColors(TealCyan)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = "icon"
            )
            Spacer(modifier=Modifier.size(3.dp))
            Text(text = text, fontSize = 15.sp)
        }
    }
}