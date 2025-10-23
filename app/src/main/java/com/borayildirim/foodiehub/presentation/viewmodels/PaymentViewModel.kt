package com.borayildirim.foodiehub.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borayildirim.foodiehub.domain.model.CardType
import com.borayildirim.foodiehub.domain.model.OrderSummary
import com.borayildirim.foodiehub.domain.model.PaymentCard
import com.borayildirim.foodiehub.domain.usecase.ClearCartUseCase
import com.borayildirim.foodiehub.domain.usecase.GetCartItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PaymentUiState(
    val orderSummary: OrderSummary = OrderSummary(0.0, 0.0, 0.0),
    val availableCards: List<PaymentCard> = emptyList(),
    val selectedCardId: String? = null,
    val saveCardForFeature: Boolean = false,
    val isProcessing: Boolean = false,
    val paymentSuccess: Boolean = false
)

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val getCartItemUseCase: GetCartItemsUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadPaymentData()
    }

    private fun loadPaymentData() {
        viewModelScope.launch {
            // Load cart items and calculate order summary
            getCartItemUseCase().collect { cartItems ->
                val subtotal = cartItems.sumOf { it.totalPrice }
                val tax = subtotal * 0.02 // 2% tax
                val deliveryFee = 1.5

                val orderSummary = OrderSummary(
                    subtotal = subtotal,
                    tax = tax,
                    deliveryFee = deliveryFee
                )

                // Mock payment cards (in real app, fetch from database)
                val cards = listOf(
                    PaymentCard(
                        id = "1",
                        cardNumber = "5105 **** **** 0505",
                        cardHolderName = "John Doe",
                        expiryDate = "12/25",
                        cardType = CardType.MASTERCARD,
                        isDefault = true
                    ),
                    PaymentCard(
                        id = "2",
                        cardNumber = "3566 **** **** 0505",
                        cardHolderName = "John Doe",
                        expiryDate = "08/26",
                        cardType = CardType.VISA,
                        isDefault = false
                    )
                )

                _uiState.value = _uiState.value.copy(
                    orderSummary = orderSummary,
                    availableCards = cards,
                    selectedCardId = cards.firstOrNull { it.isDefault } ?.id
                )
            }
        }
    }

    fun selectCard(cardId: String) {
        _uiState.value = _uiState.value.copy(selectedCardId = cardId)
    }

    fun toggleSaveCard() {
        _uiState.value = _uiState.value.copy(
            saveCardForFeature = !_uiState.value.saveCardForFeature
        )
    }

    fun processPayment() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isProcessing = true)

            // Simulate payment processing
            kotlinx.coroutines.delay(2000)

            // Clear cart after successful payment
            clearCartUseCase()

            _uiState.value = _uiState.value.copy(
                isProcessing = false,
                paymentSuccess = true
            )
        }
    }

    fun resetPaymentSuccess() {
        _uiState.value = _uiState.value.copy(paymentSuccess = false)
    }
}