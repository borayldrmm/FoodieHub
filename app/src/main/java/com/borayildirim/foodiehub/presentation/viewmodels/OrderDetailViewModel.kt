package com.borayildirim.foodiehub.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borayildirim.foodiehub.domain.model.Order
import com.borayildirim.foodiehub.domain.usecase.GetOrderByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  UI State for Order Detail Screen
 *
 *  @property order Order details
 *  @property isLoading Whether data is being loaded
 *  @property error Error message if any
 */
data class OrderDetailUiState(
    val order: Order? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

/**
 * ViewModel for Order Detail Screen
 *
 * Manage single order details display
 *
 * @property getOrderByIdUseCase Use case to get order by ID
 */
@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val getOrderByIdUseCase: GetOrderByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderDetailUiState())
    val uiState = _uiState.asStateFlow()

    /**
     * Loads order details by ID
     *
     * @param orderId Order ID to load
     */
    fun loadOrder(orderId: String) {
        viewModelScope.launch {
            try {
                getOrderByIdUseCase(orderId).collect { order->
                    _uiState.value = OrderDetailUiState(
                        order = order,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = OrderDetailUiState(
                    order = null,
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}