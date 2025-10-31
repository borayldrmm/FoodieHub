package com.borayildirim.foodiehub.presentation.screens

import android.app.Activity
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.domain.model.User
import com.borayildirim.foodiehub.presentation.navigation.Route
import com.borayildirim.foodiehub.presentation.ui.components.profile.ChangePasswordBottomSheet
import com.borayildirim.foodiehub.presentation.ui.components.profile.LogoutConfirmationDialog
import com.borayildirim.foodiehub.presentation.ui.components.profile.PasswordField
import com.borayildirim.foodiehub.presentation.ui.components.profile.ProfileFormField
import com.borayildirim.foodiehub.presentation.ui.components.profile.ProfileNavigationCard
import com.borayildirim.foodiehub.presentation.utils.rememberImagePickerHelper
import com.borayildirim.foodiehub.presentation.viewmodels.ProfileViewModel
import com.composables.icons.lucide.Cog
import com.composables.icons.lucide.LogOut
import com.composables.icons.lucide.Lucide
import com.yalantis.ucrop.UCrop
import java.io.File

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by profileViewModel.uiState.collectAsState()
    val isLoggedIn by profileViewModel.isLoggedIn.collectAsState()

    when {
        !isLoggedIn -> {
            // Guest screen - not logged in
            ProfileGuestScreen(navController)
        }

        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Hata: ${uiState.error}")
            }
        }

        uiState.user != null -> {
            ProfileContent(
                user = uiState.user!!,
                editableUser = uiState.editableUser,
                isEditing = uiState.isEditing,
                onBackClick = { navController.popBackStack() },
                onPaymentDetailsClick = { /* Payment details navigation */ },
                onOrderHistoryClick = {
                    navController.navigate(Route.OrderHistory.route)
                },
                onEditProfileClick = { profileViewModel.toggleEditMode() },
                onSaveClick = { profileViewModel.saveProfile() },
                onCancelClick = { profileViewModel.cancelEdit() },
                onLogoutClick = { profileViewModel.logout() },
                onNameChange = { name ->
                    uiState.editableUser?.let { user ->
                        profileViewModel.updateEditableUser(user.copy(fullName = name))
                    }
                },
                onEmailChange = { email ->
                    uiState.editableUser?.let { user ->
                        profileViewModel.updateEditableUser(user.copy(email = email))
                    }
                },
                onAddressChange = { address ->
                    uiState.editableUser?.let { user ->
                        profileViewModel.updateEditableUser(user.copy(deliveryAddress = address))
                    }
                },
                onPasswordChange = { /* Handle Password */ },
                onPhoneChange = { phone ->
                    uiState.editableUser?.let { user ->
                        profileViewModel.updateEditableUser(user.copy(phoneNumber = phone))
                    }
                },
                onImageSelected = { uri ->
                    profileViewModel.updateProfileImage(uri)
                },
                profileViewModel = profileViewModel
            )
        }
    }
}

@Composable
private fun ProfileContent(
    user: User,
    editableUser: User?,
    isEditing: Boolean,
    onBackClick: () -> Unit,
    onPaymentDetailsClick: () -> Unit,
    onOrderHistoryClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onImageSelected: (Uri) -> Unit,
    profileViewModel: ProfileViewModel
) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val activity = context as Activity
    val snackbarHostState = remember { SnackbarHostState() }

    // BottomSheet state
    var showPasswordBottomSheet by remember { mutableStateOf(false) }

    // Logout dialog state
    var showLogoutDialog by remember { mutableStateOf(false) }


    val imagePickerHelper = rememberImagePickerHelper(
        onImageSelected = onImageSelected,
        snackbarHostState = snackbarHostState
    )

    val cropLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { data ->
                val resultUri = UCrop.getOutput(data)
                resultUri?.let { croppedUri ->
                    imagePickerHelper.onImageSelected(croppedUri)
                }
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { selectedUri ->

            // Create destination URI for cropped image
            val destinationUri = Uri.fromFile(
                File(context.cacheDir, "cropped_${System.currentTimeMillis()}.jpg")
            )

            // Launch UCrop activity
            val cropIntent = UCrop.of(selectedUri, destinationUri)
                .withAspectRatio(1f , 1f)
                .withMaxResultSize(512,512)
                .withOptions(UCrop.Options().apply {
                    setCircleDimmedLayer(true)  // Circular crop
                    setToolbarTitle(context.getString(R.string.crop_image))
                    setActiveControlsWidgetColor(android.graphics.Color.RED)
                })
                .getIntent(context)

            cropLauncher.launch(cropIntent)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        imagePickerHelper.handlePermissionResult(
            isGranted = isGranted,
            galleryLauncher = galleryLauncher,
            activity = activity,
        )
    }

    Box() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Gradient Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFE53E3E), // Red gradient top
                                Color(0xFFFC8181)  // Red gradient bottom
                            )
                        )
                    )
            ) {
                // Back button
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                ) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = stringResource(R.string.back_txt),
                        tint = Color.White
                    )
                }

                // Profile image
                Image(
                    painter = if (isEditing && editableUser?.profilePicture != null) {
                        rememberAsyncImagePainter(editableUser.profilePicture)
                    } else if (!isEditing && user.profilePicture != null) {
                        rememberAsyncImagePainter(user.profilePicture)
                    } else {
                        painterResource(R.drawable.profile_icon)
                    },
                    contentDescription = stringResource(R.string.profile_picture_txt),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(125.dp)
                        .clip(CircleShape)
                        .clickable(enabled = isEditing) {
                            imagePickerHelper.handleImageClick(
                                permissionLauncher = permissionLauncher,
                                galleryLauncher = galleryLauncher,
                                coroutineScope = coroutineScope
                            )
                        }
                )
            }

            // White content section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 16.dp)
            ) {

                // Form Fields
                ProfileFormField(
                    label = stringResource(R.string.name_txt),
                    value = if (isEditing) editableUser?.fullName ?: "" else user.fullName,
                    onValueChange = onNameChange,
                    enabled = isEditing
                )

                ProfileFormField(
                    label = stringResource(R.string.email),
                    value = if (isEditing) editableUser?.email ?: "" else user.email,
                    onValueChange = onEmailChange,
                    enabled = isEditing
                )

                ProfileFormField(
                    label = stringResource(R.string.delivery_adress),
                    value = if (isEditing) editableUser?.deliveryAddress ?: "" else user.deliveryAddress ?: "",
                    onValueChange = onAddressChange,
                    enabled = isEditing

                )

                ProfileFormField(
                    label = stringResource(R.string.phone_number),
                    value = if (isEditing) editableUser?.phoneNumber ?: "" else user.phoneNumber ?: "",
                    onValueChange = onPhoneChange,
                    enabled = isEditing
                )

                // Password Field
                PasswordField(
                    label = stringResource(R.string.password),
                    value = "••••••••",
                    onValueChange = {  },
                    enabled = false,
                    isEditing = isEditing,
                    onEditClick = {
                        showPasswordBottomSheet = true
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Navigation Cards
                ProfileNavigationCard(
                    title = stringResource(R.string.payment_details),
                    onClick = onPaymentDetailsClick
                )

                ProfileNavigationCard(
                    title = stringResource(R.string.order_history),
                    onClick = onOrderHistoryClick
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Action Buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {

                    // Save & Edit button
                    Button(
                        onClick = if (isEditing) onSaveClick else onEditProfileClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(65.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = if (isEditing) BorderStroke(
                            width = 2.dp,
                            color = Color.Green
                        ) else BorderStroke(
                            width = 2.dp,
                            color = Color.DarkGray
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isEditing) Color.White else Color.DarkGray
                        )
                    ) {
                        Text(
                            text = if (isEditing) stringResource(R.string.save) else stringResource(R.string.edit),
                            color = if (isEditing) Color.Black else Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Icon(
                            imageVector = if (isEditing) Icons.Default.SaveAlt else Lucide.Cog,
                            contentDescription = if (isEditing) stringResource(R.string.save) else stringResource(R.string.edit),
                            tint = if (isEditing) Color.Black else Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(2.dp))

                    // Logout & Cancel button
                    OutlinedButton(
                        onClick = if (isEditing) {
                            onCancelClick
                        } else {
                            { showLogoutDialog = true }
                        },
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(width = 2.dp, color = Color.Red),
                        modifier = Modifier
                            .weight(1f)
                            .height(65.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Red
                        )
                    ) {
                        Text(
                            text = if (isEditing) stringResource(R.string.cancel) else stringResource(R.string.logout),
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Icon(
                            imageVector = if (isEditing) Icons.Default.Cancel else Lucide.LogOut,
                            contentDescription = if (isEditing) stringResource(R.string.cancel) else stringResource(R.string.logout),
                            tint = Color.Red
                        )
                    }
                }
            }

            if (showPasswordBottomSheet) {
                ChangePasswordBottomSheet(
                    onDismiss = { showPasswordBottomSheet = false },
                    onPasswordChange = { currentPassword, newPassword, confirmPassword ->
                        profileViewModel.changePassword(
                            currentPassword = currentPassword,
                            newPassword = newPassword,
                            confirmPassword = confirmPassword,
                            onSuccess = {
                                showPasswordBottomSheet = false
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.password_changed_successfully),
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onError = { errorMessage ->
                                Toast.makeText(
                                    context,
                                    errorMessage,
                                    Toast.LENGTH_LONG
                                ).show()
                            },
                            context = context
                        )
                    }
                )
            }
        }

        // Logout Confirmation Dialog
        if (showLogoutDialog) {
            LogoutConfirmationDialog(
                onDismiss = { showLogoutDialog = false },
                onConfirm = {
                    showLogoutDialog = false
                    onLogoutClick()
                }
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}