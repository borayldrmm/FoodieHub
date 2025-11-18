package com.borayildirim.foodiehub.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borayildirim.foodiehub.data.local.preferences.UserPreferencesManager
import com.borayildirim.foodiehub.domain.model.CardType
import com.borayildirim.foodiehub.domain.model.PaymentCard
import com.borayildirim.foodiehub.domain.usecase.payment.AddPaymentCardUseCase
import com.borayildirim.foodiehub.domain.usecase.payment.DeletePaymentCardUseCase
import com.borayildirim.foodiehub.domain.usecase.payment.GetUserCardsUseCase
import com.borayildirim.foodiehub.domain.usecase.payment.SetDefaultCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.UUID
import javax.inject.Inject

data class PaymentDetailsUiState(
    val cards: List<PaymentCard> = emptyList(),
    val isLoading: Boolean = false,
    val showAddCardDialog: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PaymentDetailsViewModel @Inject constructor(
    private val getUserCardsUseCase: GetUserCardsUseCase,
    private val addPaymentCardUseCase: AddPaymentCardUseCase,
    private val deletePaymentCardUseCase: DeletePaymentCardUseCase,
    private val setDefaultCardUseCase: SetDefaultCardUseCase,
    private val userPreferencesManager: UserPreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentDetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadPaymentCards()
    }

    private fun loadPaymentCards() {
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        error = null
                    )
                }

                val userId = userPreferencesManager.getUserId().firstOrNull()

                if (userId != null) {
                    getUserCardsUseCase(userId).collect { cards ->
                        _uiState.update {
                            it.copy(
                                cards = cards,
                                isLoading = false
                            )
                        }
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "User not logged in."
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to load cards: ${e.message}"
                    )
                }
            }
        }
    }

    fun showAddCardDialog() {
        _uiState.update { it.copy(showAddCardDialog = true) }
    }

    fun hideAddCardDialog() {
        _uiState.update { it.copy(showAddCardDialog = false) }
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
            try {
                val userId = userPreferencesManager.getUserId().firstOrNull()

                if (userId != null) {
                    val newCard = PaymentCard(
                        id = UUID.randomUUID().toString(),
                        cardNumber = maskCardNumber(cardNumber),
                        cardHolderName = cardHolderName,
                        expiryDate = expiryDate,
                        cardType = cardType,
                        isDefault = setAsDefault
                    )

                    addPaymentCardUseCase(userId, newCard)

                    if (setAsDefault) {
                        setDefaultCardUseCase(userId, newCard.id)
                    }

                    hideAddCardDialog()
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "User not logged in."
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "Failed to add card: ${e.message}")
                }
            }
        }
    }

    fun deleteCard(cardId: String) {
        viewModelScope.launch {
            try {
                deletePaymentCardUseCase(cardId)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "Failed to delete card: ${e.message}")
                }
            }
        }
    }

    fun setDefaultCard(cardId: String) {
        viewModelScope.launch {
            try {
                val userId = userPreferencesManager.getUserId().firstOrNull()

                if (userId != null) {
                    setDefaultCardUseCase(userId, cardId)
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "User not logged in."
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "Failed to set default card: ${e.message}")
                }
            }
        }
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