package com.example.travelapp.ui.home.profile


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.travelapp.R
import com.example.travelapp.ui.components.BackButton
import com.example.travelapp.ui.components.CustomDatePickerField
import com.example.travelapp.ui.components.CustomEditText
import com.example.travelapp.ui.components.GenderDropdown
import com.example.travelapp.ui.theme.TealCyan
import com.example.travelapp.ui.utils.uriToMultipart


@Composable
fun ProfileTabScreen(
    viewModel: ProfileViewModel=hiltViewModel(),
    onNavigateBack:()->Unit
) {

    val profile by viewModel.profile.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context=LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }


    val launcher=rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {uri: Uri?->
            selectedImageUri=uri
        }
    )

    LaunchedEffect(Unit) {
        viewModel.fetchProfile()
    }

    var profilePic by remember { mutableStateOf("") }
    var fullname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }


    LaunchedEffect(profile) {
        profile?.let {
            profilePic = it.profilePic ?: ""
            fullname = it.fullname
            email = it.email
            dob = it.dob ?: ""
            phone = it.phone ?: ""
            gender = it.gender ?: ""
            city = it.city ?: ""
            state = it.state ?: ""
            country = it.country ?: ""
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BackButton(
                onClick = {onNavigateBack()}
            )
            Spacer(modifier = Modifier.padding(start = 70.dp))
            Text(
                text = "Edit Profile",
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
        }
        if (uiState is ProfileViewModel.ProfileEvent.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(30.dp),
                color = TealCyan,
                strokeWidth = 2.dp
            )
        }

        profile?.let {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                ProfileImageCard(
                    profilePic=selectedImageUri?:profilePic,
                    onImageClick={
                        launcher.launch("image/*")
                    }
                )

                GeneralDetails(
                    fullname,
                    dob,
                    gender,
                    onFullNameChange = { fullname = it },
                    onDOBChange = { dob = it },
                    onGenderChange = { gender = it }
                )

                HorizontalDivider(
                    color = Color.Black.copy(alpha = 0.1f),
                    thickness = 2.dp,
                    modifier = Modifier.padding(vertical = 15.dp)
                )

                ContactDetails(
                    phone,
                    email,
                    onPhoneChange = { phone = it },
                )

                HorizontalDivider(
                    color = Color.Black.copy(alpha = 0.1f),
                    thickness = 2.dp,
                    modifier = Modifier.padding(vertical = 15.dp)
                )

                AddressDetails(
                    city,
                    state,
                    country,
                    onCityChange = { city = it },
                    onStateChange = { state = it },
                    onCountryChange = { country = it }
                )


            }

            Button(
                onClick = {
                    val imagePart=selectedImageUri?.let{
                        uriToMultipart(context,it)
                    }
                    viewModel.updateProfile(
                        imagePart,
                        fullname,
                        dob,
                        gender,
                        phone,
                        city,
                        state,
                        country
                    )
                },
                modifier = Modifier
                    .padding(bottom = 15.dp, start = 30.dp, end = 30.dp)
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
}

@Composable
fun AddressDetails(
    city: String,
    state: String,
    country: String,
    onCityChange: (String) -> Unit,
    onStateChange: (String) -> Unit,
    onCountryChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Address Details",
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth(),
        )

        CustomEditText(
            modifier = Modifier.fillMaxWidth(),
            label={ Text("City Of Residence") },
            value=city,
            onValueChange = onCityChange,
            )
        CustomEditText(
            modifier = Modifier.fillMaxWidth(),
            label={ Text("State") },
            value=state,
            onValueChange = onStateChange,
        )
        CustomEditText(
                modifier = Modifier.fillMaxWidth(),
        label={ Text("Country") },
        value=country,
        onValueChange = onCountryChange,
        )

    }
}

@Composable
fun ContactDetails(
    phone: String,
    email: String,
    onPhoneChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Contact Details",
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth(),
        )

        CustomEditText(
            modifier = Modifier.fillMaxWidth(),
            label={ Text("Phone Number") },
            value=phone,
            onValueChange = onPhoneChange,
            )

        Spacer(modifier=Modifier.size(15.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label={ Text("Email") },
            value=email,
            onValueChange = {},
            readOnly = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TealCyan,
                focusedBorderColor = TealCyan,
                focusedLabelColor = TealCyan
            )
        )

        Text(
            text = "You Can't change Email here",
            fontSize = 10.sp,
            color = Color.Black.copy(alpha = 0.4f),
            modifier = Modifier.fillMaxWidth()
            )
    }
}


@Composable
fun GeneralDetails(
    fullname: String,
    dob: String,
    gender: String,
    onFullNameChange: (String) -> Unit,
    onDOBChange: (String) -> Unit,
    onGenderChange: (String) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "General Details",
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = "Fields marked with * are mandatory",
            fontSize = 12.sp,
            color = Color.Black.copy(alpha = 0.4f)
        )

        CustomEditText(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Full Name") },
            value = fullname,
            onValueChange = onFullNameChange,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomDatePickerField (
                modifier = Modifier.weight(1f).padding(top = 15.dp),
                selectedDate = dob,
                onDateSelected = onDOBChange
            )

            GenderDropdown(
                modifier = Modifier.weight(1f).padding(top = 15.dp),
                selectedGender = gender,
                onGenderChange = onGenderChange
            )
        }

    }
}


@Composable
fun ProfileImageCard(
    profilePic: Any?,
    onImageClick:()->Unit
) {
    Box(
        modifier = Modifier.padding(bottom = 80.dp, top = 20.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Card(
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(10.dp),
            modifier = Modifier.size(120.dp)
        ) {
            AsyncImage(
                model = profilePic,
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
                .clickable {
                   onImageClick()
                },
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


