package com.example.travelapp.ui.home.navigationDrawer.myaccount

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbnb.lottie.model.content.CircleShape
import com.example.travelapp.ui.components.AuthInputText
import com.example.travelapp.ui.components.CustomTopBar
import com.example.travelapp.ui.home.navigation.MyProfileTab
import com.example.travelapp.ui.home.profile.SectionCard
import com.example.travelapp.ui.theme.TealCyan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAccountScreen(
    navController: NavController,
    viewModel: MyAccountViewModel = hiltViewModel()
) {

    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(false) }
    val showChangePasswordDialog = remember { mutableStateOf(false) }
    val showDeleteDialog = remember { mutableStateOf(false) }
    val showLogoutDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        CustomTopBar(
            title = "My Account", onBackClick = { navController.popBackStack() })


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            SectionCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), title = null, content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        AsyncImage(
                            model = "https://picsum.photos/200",
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(90.dp),
                        )
                        Column(
                            modifier = Modifier.padding(start = 10.dp),
                        ) {
                            Text(
                                text = "Siddharth Kushwaha",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Row(
                                modifier = Modifier.clickable(
                                    onClick = {
                                        navController.navigate(MyProfileTab)
                                    }), verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = TealCyan,
                                    modifier = Modifier.size(17.dp)
                                )
                                Text(
                                    text = "Edit Profile",
                                    fontSize = 13.sp,
                                    color = TealCyan,
                                    modifier = Modifier.padding(start = 5.dp)
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(17.dp)
                                )
                                Text(
                                    text = "+91-7380339254",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(start = 5.dp)
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(17.dp)
                                )
                                Text(
                                    text = "Siddharthkush12@gmail.com",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(start = 5.dp)
                                )
                            }
                        }
                    }
                })

            SectionCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                title = "Account Setting",
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    showBottomSheet.value = true
                                }),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Default.ManageAccounts,
                                contentDescription = null,
                                tint = TealCyan,
                                modifier = Modifier.size(27.dp)
                            )
                            Text(
                                text = "Accounts Management",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = TealCyan,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().clickable(
                            onClick = {
                                showLogoutDialog.value = true
                            }
                        ),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Logout,
                                contentDescription = null,
                                tint = TealCyan,
                                modifier = Modifier.size(27.dp)
                            )
                            Text(
                                text = "LogOut", fontSize = 17.sp, fontWeight = FontWeight.SemiBold
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = TealCyan,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            )
        }



        if (showBottomSheet.value) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet.value = false },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Account Management",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showChangePasswordDialog.value = true
                            }
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LockReset,
                            contentDescription = null,
                            tint = TealCyan
                        )
                        Text(
                            text = "Change Password",
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showDeleteDialog.value = true
                            }
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteForever,
                            contentDescription = null,
                            tint = Color.Red
                        )
                        Text(
                            text = "Delete Account",
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                }
            }
        }



        if (showChangePasswordDialog.value) {

            val oldPassword by viewModel.oldPassword.collectAsStateWithLifecycle()
            val newPassword by viewModel.newPassword.collectAsStateWithLifecycle()

            AlertDialog(
                onDismissRequest = { showChangePasswordDialog.value = false },

                shape = RoundedCornerShape(18.dp),
                containerColor = Color.White,

                title = {
                    Column {
                        Text(
                            text = "Change Password",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Update your account password securely",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                },

                text = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.padding(top = 6.dp)
                    ) {

                        AuthInputText(
                            value = oldPassword,
                            onValueChange = {
                                viewModel.onOldPasswordChange(it)
                            },
                            label = "Old Password",
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = "Old Password",
                            keyboardOption = KeyboardOptions.Default
                        )

                        AuthInputText(
                            value = newPassword,
                            onValueChange = {
                                viewModel.onNewPasswordChange(it)
                            },
                            label = "New Password",
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = "New Password",
                            keyboardOption = KeyboardOptions.Default
                        )
                    }
                },

                confirmButton = {

                    Button(
                        onClick = { viewModel.changePassword() },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TealCyan),
                        modifier = Modifier.height(45.dp)
                    ) {
                        Text(
                            text = "Update",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                },

                dismissButton = {

                    TextButton(
                        onClick = { showChangePasswordDialog.value = false }
                    ) {
                        Text(
                            text = "Cancel",
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            )
        }

        if (showDeleteDialog.value) {

            AlertDialog(
                onDismissRequest = { showDeleteDialog.value = false },

                shape = RoundedCornerShape(18.dp),
                containerColor = Color.White,

                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Icon(
                            imageVector = Icons.Default.DeleteForever,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.size(40.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Delete Account",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                },

                text = {
                    Text(
                        text = "This action is permanent and cannot be undone. All your data will be permanently deleted.",
                        fontSize = 13.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },

                confirmButton = {

                    Button(
                        onClick = { viewModel.deleteAccount() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(45.dp)
                    ) {
                        Text(
                            text = "Delete",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                },

                dismissButton = {

                    TextButton(
                        onClick = { showDeleteDialog.value = false }
                    ) {
                        Text(
                            text = "Cancel",
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            )
        }



        if (showLogoutDialog.value) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog.value = false },

                shape = RoundedCornerShape(18.dp),
                containerColor = Color.White,

                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = null,
                            tint = TealCyan,
                            modifier = Modifier.size(28.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Logout",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },

                text = {
                    Text(
                        text = "Are you sure you want to logout from your account?",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                },

                confirmButton = {

                    Button(
                        onClick = {
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = TealCyan),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(45.dp)
                    ) {
                        Text(
                            text = "Logout",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                },

                dismissButton = {

                    TextButton(
                        onClick = { showLogoutDialog.value = false }
                    ) {
                        Text(
                            text = "Cancel",
                            color = Color.Gray
                        )
                    }
                }
            )
        }
    }
}




