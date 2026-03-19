package com.example.travelapp.ui.home.profile


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.travelapp.ui.components.CustomTopBar
import com.example.travelapp.ui.components.GenderDropdown
import com.example.travelapp.ui.theme.TealCyan
import com.example.travelapp.ui.utils.uriToMultipart


@Composable
fun ProfileTabScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {

    val profile by viewModel.profile.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> selectedImageUri = uri }

    LaunchedEffect(Unit) {
        viewModel.fetchProfile()
    }

    var fullname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var profilePic by remember { mutableStateOf("") }

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
            .background(Color(0xFFF5F7FA))
    ) {

        CustomTopBar(
            title = "Profile",
            onBackClick = onNavigateBack
        )

        if (uiState is ProfileViewModel.ProfileEvent.Loading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = TealCyan)
            }
            return
        }

        profile?.let {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {


                ProfileImageCard(
                    profilePic = selectedImageUri ?: profilePic,
                    onImageClick = { launcher.launch("image/*") }
                )


                SectionCard(
                    modifier = Modifier.fillMaxWidth(),
                    "General Information"
                ) {
                    GeneralDetails(
                        fullname, dob, gender,
                        { fullname = it },
                        { dob = it },
                        { gender = it }
                    )
                }


                SectionCard(
                    modifier = Modifier.fillMaxWidth(),
                    "Contact"
                ) {
                    ContactDetails(
                        phone, email,
                        { phone = it }
                    )
                }


                SectionCard(
                    modifier = Modifier.fillMaxWidth(),
                    "Address"
                ) {
                    AddressDetails(
                        city, state, country,
                        { city = it },
                        { state = it },
                        { country = it }
                    )
                }


                Button(
                    onClick = {
                        val imagePart = selectedImageUri?.let {
                            uriToMultipart(context, it)
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
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(TealCyan)
                ) {
                    Text("Save Changes", fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}


@Composable
fun SectionCard(
    modifier: Modifier = Modifier,
    title: String?,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {

        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            title?.let {
                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            content()
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

    Column {

        CustomEditText(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("City") },
            value = city,
            onValueChange = onCityChange
        )

        CustomEditText(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("State") },
            value = state,
            onValueChange = onStateChange
        )

        CustomEditText(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Country") },
            value = country,
            onValueChange = onCountryChange
        )
    }
}

@Composable
fun ContactDetails(
    phone: String,
    email: String,
    onPhoneChange: (String) -> Unit,
) {

    Column {

        CustomEditText(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Phone Number") },
            value = phone,
            onValueChange = onPhoneChange
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {},
            readOnly = true,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            "Email cannot be changed",
            fontSize = 11.sp,
            color = Color.Gray
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

    Column {
        CustomEditText(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Full Name") },
            value = fullname,
            onValueChange = onFullNameChange
        )

        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {

            CustomDatePickerField(
                modifier = Modifier.weight(1.5f),
                selectedDate = dob,
                onDateSelected = onDOBChange
            )

            GenderDropdown(
                modifier = Modifier.weight(1f),
                selectedGender = gender,
                onGenderChange = onGenderChange
            )
        }
    }
}

@Composable
fun ProfileImageCard(
    profilePic: Any?,
    onImageClick: () -> Unit
) {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        Box {

            AsyncImage(
                model = profilePic,
                contentDescription = null,
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(3.dp, TealCyan, CircleShape),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(TealCyan)
                    .clickable { onImageClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.camera_svgrepo_com),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}