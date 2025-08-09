package com.borayildirim.foodiehub.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor (): ViewModel() {

    private val _shouldNavigate = MutableStateFlow(false)
    val shouldNavigate = _shouldNavigate.asStateFlow()

    fun startSplash() {
        viewModelScope.launch {
            delay(2500)
            _shouldNavigate.value = true

        }
    }
}