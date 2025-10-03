package com.borayildirim.foodiehub.presentation.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.borayildirim.foodiehub.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ImagePickerHelper(
    private val context: Context,
    val onImageSelected: (Uri) -> Unit,
    private val snackbarHostState: SnackbarHostState
) {
    private val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // Permission state tracking
    private var permissionRequested = false

    fun handleImageClick(
        permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
        galleryLauncher: ManagedActivityResultLauncher<String, Uri?>,
        coroutineScope: CoroutineScope
    ) {
        when (ContextCompat.checkSelfPermission(context, permission)) {
            PackageManager.PERMISSION_GRANTED -> {
                galleryLauncher.launch("image/*")
            }
            else -> {
                if (permissionRequested) {
                    // Second click - show SnackBar
                    coroutineScope.launch {
                        val actionPerformed = showPermissionRationale()
                        if(actionPerformed) {
                            permissionLauncher.launch(permission)
                        }
                    }
                } else {
                    // First time click - want to permission directly
                    permissionRequested = true
                    permissionLauncher.launch(permission)
                }
            }
        }
    }

    fun handlePermissionResult(
        isGranted: Boolean,
        galleryLauncher: ManagedActivityResultLauncher<String, Uri?>,
        activity: Activity
    ) {
        if (isGranted) {
            galleryLauncher.launch("image/*") // First open to gallery
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                // Show Toast - can request again
                Toast.makeText(context, context.getString(R.string.need_gallery_permission), Toast.LENGTH_LONG).show()
            } else {
                // Permanently denied - direct to settings
                Toast.makeText(context, context.getString(R.string.give_permission_in_settings), Toast.LENGTH_LONG).show()
            }
        }
    }

    private suspend fun showPermissionRationale(): Boolean {
        val result = snackbarHostState.showSnackbar(
            message = context.getString(R.string.need_gallery_permission),
            actionLabel = context.getString(R.string.give_permission)
        )
        return result == SnackbarResult.ActionPerformed
    }
}

@Composable
fun rememberImagePickerHelper(
    onImageSelected: (Uri) -> Unit,
    snackbarHostState: SnackbarHostState
): ImagePickerHelper {
    val context = LocalContext.current
    return remember {
        ImagePickerHelper(context, onImageSelected, snackbarHostState)
    }
}