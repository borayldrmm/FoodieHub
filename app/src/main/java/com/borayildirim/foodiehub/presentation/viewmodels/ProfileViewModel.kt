package com.borayildirim.foodiehub.presentation.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.domain.model.User
import com.borayildirim.foodiehub.domain.repository.ProfileRepository
import com.borayildirim.foodiehub.domain.usecase.CheckUserLoginStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEditing: Boolean = false,
    val editableUser: User? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val checkUserLoginStatusUseCase: CheckUserLoginStatusUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    init {
        checkLoginStatus()
    }
    fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val user = profileRepository.getCurrentUser()
                _uiState.value = _uiState.value.copy(
                    user = user,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun toggleEditMode() {
        val currentUser = _uiState.value.user
        _uiState.value = _uiState.value.copy(
            isEditing = !_uiState.value.isEditing,
            editableUser = if (!_uiState.value.isEditing) currentUser else null
        )
    }

    fun updateEditableUser(updateUser: User) {
        _uiState.value = _uiState.value.copy(editableUser = updateUser)
    }

    fun saveProfile() {
        val editableUser = _uiState.value.editableUser
        if (editableUser != null) {
            viewModelScope.launch {
                try {
                    profileRepository.updateUserProfile(editableUser)
                    _uiState.value = _uiState.value.copy(
                        user = editableUser,
                        isEditing = false,
                        editableUser = null
                    )
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(error = e.message)
                }
            }
        }
    }

    fun cancelEdit() {
        _uiState.value = _uiState.value.copy(
            isEditing = false,
            editableUser = null
        )
    }

    fun logout() {
        viewModelScope.launch {
            try {
                profileRepository.logout()
                _isLoggedIn.value = false
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun updateProfileImage(imageUri: Uri) {
        val currentUser = _uiState.value.editableUser ?: return

        // Open edit mode
        if (!_uiState.value.isEditing) {
            _uiState.value = _uiState.value.copy(
                isEditing = true,
                editableUser = currentUser.copy(
                    profilePicture = imageUri.toString()
                )
            )
        } else {
            _uiState.value.editableUser?.let { user ->
                updateEditableUser(user.copy(
                    profilePicture = imageUri.toString()
                ))
            }
        }
    }

    fun changePassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
        context: Context
    ) {
        viewModelScope.launch {
            // Validation
            when {
                currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty() -> {
                    onError(context.getString(R.string.fill_all_fields))
                    return@launch
                }
                newPassword != confirmPassword -> {
                    onError(context.getString(R.string.passwords_do_not_match))
                    return@launch
                }
                newPassword.length < 6 -> {
                    onError(context.getString(R.string.pw_mustbe_atleast_6_characters))
                    return@launch
                }
                else ->
                    try {
                        // Backend call
                        profileRepository.changePassword(currentPassword, newPassword)
                        onSuccess()
                    } catch (e: Exception) {
                        onError(e.message ?: context.getString(R.string.wrong_current_pw))
                    }
            }
        }
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            _isLoggedIn.value = checkUserLoginStatusUseCase.checkLoginStatus()
            if (_isLoggedIn.value) {
                loadUserProfile()
            }
        }
    }
}