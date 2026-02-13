package com.example.travelapp.ui.home.profile


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelapp.R
import com.example.travelapp.ui.components.BackButton
import com.example.travelapp.ui.components.CustomEditText
import com.example.travelapp.ui.theme.TealCyan


@Preview(showSystemUi = true)
@Composable
fun ProfileTabScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BackButton(
                onClick = {}
            )
            Spacer(modifier = Modifier.padding(start = 70.dp))
            Text(
                text = "Edit Profile",
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                ProfileImageCard()
            }

            item {
                GeneralDetails()
            }
            item {
                HorizontalDivider(
                    color = Color.Black.copy(alpha = 0.1f),
                    thickness = 2.dp,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                )
            }

            item{
                ContactDetails()
            }

            item {
                HorizontalDivider(
                    color = Color.Black.copy(alpha = 0.1f),
                    thickness = 2.dp,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                )
            }

            item{
                AddressDetails()
            }




        }

        Button(
            onClick = {},
            modifier = Modifier
                .padding(bottom = 10.dp, start = 30.dp, end = 30.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(TealCyan),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Save Changes"
            )
        }

    }
}

@Composable
fun AddressDetails() {
    Column(
        modifier = Modifier.padding(bottom = 30.dp)
    ) {
        Text(
            text = "Address Details",
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth(),
        )

        CustomEditText()

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomEditText(modifier = Modifier.weight(1f))
            CustomEditText(modifier = Modifier.weight(1f))
        }
        CustomEditText()
    }
}

@Composable
fun ContactDetails() {
    Column() {
        Text(
            text = "Contact Details",
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth(),
        )

        CustomEditText()

        CustomEditText()
    }
}

@Composable
fun GeneralDetails() {

    Column() {
        Text(
            text = "General Details",
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = "Fields marked with * are mandatory",
            fontSize = 12.sp,
            color = Color.Black.copy(alpha = 0.4f)
        )

        CustomEditText()

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomEditText(modifier = Modifier.weight(1f))
            CustomEditText(modifier = Modifier.weight(1f))
        }
        CustomEditText()
    }
}




@Composable
fun ProfileImageCard() {
    Box(
        modifier = Modifier.padding(bottom = 80.dp, top = 20.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Card(
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(10.dp),
            modifier = Modifier.size(120.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Box(
            modifier = Modifier
                .offset((-42).dp, (13).dp)
                .size(35.dp)
                .background(Color.White.copy(alpha = 0.8f), CircleShape)
                .clickable { },
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.camera_svgrepo_com),
                contentDescription = "Edit",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}


