package com.borayildirim.foodiehub.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borayildirim.foodiehub.data.local.preferences.UserPreferencesManager
import com.borayildirim.foodiehub.domain.model.Order
import com.borayildirim.foodiehub.domain.usecase.order.GetUserOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI State for Order History Screen
 *
 * @property orders List of user's orders
 * @property isLoading Whether data is being loaded
 * @property error Error message if any
 */
data class OrderHistoryUiState(
    val orders: List<Order> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

/**
 * ViewModel for Order History Scren
 *
 * Manage user's order history with reactive Flow updates
 *
 * @property getUserOrdersUseCase Use case to get user's orders
 * @property userPreferencesManager Manager for user preferences
 *
 */
@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val getUserOrdersUseCase: GetUserOrdersUseCase,
    private val userPreferencesManager: UserPreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderHistoryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadOrders()
    }

    /**
     * Loads user's orders from database
     *
     * Update UI state with orders or error message
     */
    private fun loadOrders() {
        viewModelScope.launch {
            try {
                val userId = userPreferencesManager.getUserId().first()

                if (userId != null) {
                    getUserOrdersUseCase(userId).collect { orders ->
                        _uiState.value = OrderHistoryUiState(
                            orders = orders,
                            isLoading = false,
                            error = null
                        )
                    }
                } else {
                    _uiState.value = OrderHistoryUiState(
                        orders = emptyList(),
                        isLoading = false,
                        error = "Kullanıcı girişi yapılmamış"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = OrderHistoryUiState(
                    orders = emptyList(),
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    /**
     * Refreshes order list
     */
    fun refresh() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        loadOrders()
    }
}