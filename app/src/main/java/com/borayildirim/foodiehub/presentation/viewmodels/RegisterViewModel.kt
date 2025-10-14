package com.borayildirim.foodiehub.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.domain.model.AuthException
import com.borayildirim.foodiehub.domain.model.User
import com.borayildirim.foodiehub.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class RegisterUiState(
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val registerSuccess: Boolean = false
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onFullNameChange(name: String) {
        _uiState.value = _uiState.value.copy(fullName = name, errorMessage = null)
    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email, errorMessage = null)
    }

    fun onPhoneChange(phone: String) {
        // Only allow digits, max 10
        val digits = phone.filter { it.isDigit() }.take(10)
        _uiState.value = _uiState.value.copy(phone = digits, errorMessage = null)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password, errorMessage = null)
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword, errorMessage = null)
    }

    fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
    }

    fun toggleConfirmPasswordVisibility() {
        _uiState.value = _uiState.value.copy(isConfirmPasswordVisible = !_uiState.value.isConfirmPasswordVisible)
    }

    fun register(context: Context) {
        val fullName = _uiState.value.fullName.trim()
        val email = _uiState.value.email.trim()
        val phone = _uiState.value.phone
        val password = _uiState.value.password
        val confirmPassword = _uiState.value.confirmPassword

        // Validation
        when {
            fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() -> {
                _uiState.value = _uiState.value.copy(errorMessage = context.getString(R.string.fill_all_fields))
                return
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _uiState.value = _uiState.value.copy(errorMessage = context.getString(R.string.invalid_email))
                return
            }
            phone.length != 10 ->  {
                _uiState.value = _uiState.value.copy(errorMessage = context.getString(R.string.phone_mustbe_10))
                return
            }
            password.length < 6 -> {
                _uiState.value = _uiState.value.copy(errorMessage = context.getString(R.string.pw_mustbe_atleast_6_characters))
                return
            }
            password != confirmPassword -> {
                _uiState.value = _uiState.value.copy(errorMessage = context.getString(R.string.passwords_do_not_match))
                return
            }
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            val newUser = User(
                userId = UUID.randomUUID().toString(),
                fullName = fullName,
                email = email,
                phoneNumber = phone,
                password = password,
                deliveryAddress = null,
                profilePicture = null
            )

            userRepository.register(newUser)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        registerSuccess = true
                    )
                }
                .onFailure { error ->
                    val message = when (error) {
                        is AuthException.EmailAlreadyExists -> context.getString(R.string.email_already_exists)
                        else -> error.message ?: context.getString(R.string.unknown_error)
                    }
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = message
                    )
                }
        }
    }
}