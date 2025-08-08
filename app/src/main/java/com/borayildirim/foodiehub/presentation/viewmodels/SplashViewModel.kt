package com.borayildirim.foodiehub.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borayildirim.foodiehub.domain.usecase.CheckUserLoginStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor (
    private val checkUserLoginStatusUseCase: CheckUserLoginStatusUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(SplashState())
    val uiState = _uiState.asStateFlow()

    data class SplashState(
        val isLoading: Boolean = true,
        val shouldNavigateToHome: Boolean = false,
        val shouldNavigateToAuth: Boolean = false
    )

    fun startSplash() {
        viewModelScope.launch {
            val isLoggedIn = checkUserLoginStatusUseCase.checkLoginStatus()
            delay(2000)

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                shouldNavigateToHome = isLoggedIn,
                shouldNavigateToAuth = !isLoggedIn
            )
        }
    }
}