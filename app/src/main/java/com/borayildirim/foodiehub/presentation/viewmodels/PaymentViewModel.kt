package com.borayildirim.foodiehub.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borayildirim.foodiehub.data.local.preferences.UserPreferencesManager
import com.borayildirim.foodiehub.domain.model.CardType
import com.borayildirim.foodiehub.domain.model.OrderSummary
import com.borayildirim.foodiehub.domain.model.PaymentCard
import com.borayildirim.foodiehub.domain.usecase.AddPaymentCardUseCase
import com.borayildirim.foodiehub.domain.usecase.ClearCartUseCase
import com.borayildirim.foodiehub.domain.usecase.DeletePaymentCardUseCase
import com.borayildirim.foodiehub.domain.usecase.GetCartItemsUseCase
import com.borayildirim.foodiehub.domain.usecase.GetUserCardsUseCase
import com.borayildirim.foodiehub.domain.usecase.SetDefaultCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class PaymentUiState(
    val orderSummary: OrderSummary = OrderSummary(0.0, 0.0, 0.0),
    val availableCards: List<PaymentCard> = emptyList(),
    val selectedCardId: String? = null,
    val isProcessing: Boolean = false,
    val paymentSuccess: Boolean = false,
    val showAddCardDialog: Boolean = false
)

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val getUserCardsUseCase: GetUserCardsUseCase,
    private val addPaymentCardUseCase: AddPaymentCardUseCase,
    private val deletePaymentCardUseCase: DeletePaymentCardUseCase,
    private val setDefaultCardUseCase: SetDefaultCardUseCase,
    private val userPreferencesManager: UserPreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadPaymentData()
    }

    private fun loadPaymentData() {
        viewModelScope.launch {
            val userId = userPreferencesManager.getUserId().first()

            if (userId != null) {
                // Launch separate coroutines for cart and cards
                launch {
                    getCartItemsUseCase().collect { cartItems ->
                        val subtotal = cartItems.sumOf { it.totalPrice }
                        val tax = subtotal * 0.02
                        val deliveryFee = 1.5

                        val orderSummary = OrderSummary(
                            subtotal = subtotal,
                            tax = tax,
                            deliveryFee = deliveryFee
                        )

                        _uiState.value = _uiState.value.copy(
                            orderSummary = orderSummary
                        )
                    }
                }

                launch {
                    getUserCardsUseCase(userId).collect { cards ->
                        _uiState.value = _uiState.value.copy(
                            availableCards = cards,
                            selectedCardId = cards.firstOrNull { it.isDefault }?.id
                        )
                    }
                }
            }
        }
    }

    fun selectCard(cardId: String) {
        _uiState.value = _uiState.value.copy(selectedCardId = cardId)
    }

    fun showAddCardDialog() {
        _uiState.value = _uiState.value.copy(showAddCardDialog = true)
    }

    fun hideAddCardDialog() {
        _uiState.value = _uiState.value.copy(showAddCardDialog = false)
    }

    fun addPaymentCard(
        cardNumber: String,
        cardHolderName: String,
        expiryDate: String,
        cvv: String,
        cardType: CardType,
        setAsDefault: Boolean
    ) {
        viewModelScope.launch {
            val userId = userPreferencesManager.getUserId().first()

            println("DEBUG: Adding card - userId: $userId") // ✅ EKLE
            println("DEBUG: Card number: $cardNumber") // ✅ EKLE
            println("DEBUG: Holder: $cardHolderName") // ✅ EKLE

            if (userId != null) {
                val newCard = PaymentCard(
                    id = UUID.randomUUID().toString(),
                    cardNumber = maskCardNumber(cardNumber),
                    cardHolderName = cardHolderName,
                    expiryDate = expiryDate,
                    cardType = cardType,
                    isDefault = setAsDefault
                )

                println("DEBUG: New card created: ${newCard.id}") // ✅ EKLE

                addPaymentCardUseCase(userId, newCard)

                println("DEBUG: Card added to database") // ✅ EKLE


                if (setAsDefault) {
                    setDefaultCardUseCase(userId, newCard.id)
                    println("DEBUG: Set as default") // ✅ EKLE
                }

                hideAddCardDialog()
            } else {
                println("DEBUG: userId is NULL!") // ✅ EKLE
            }
        }
    }

    fun deleteCard(cardId: String) {
        viewModelScope.launch {
            deletePaymentCardUseCase(cardId)
        }
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

    private fun maskCardNumber(cardNumber: String): String {
        val cleaned = cardNumber.replace(" ", "")
        return if (cleaned.length == 16) {
            "${cleaned.substring(0, 4)} **** **** ${cleaned.substring(12)}"
        } else {
            cardNumber
        }
    }
}