package com.borayildirim.foodiehub.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borayildirim.foodiehub.data.local.preferences.UserPreferencesManager
import com.borayildirim.foodiehub.domain.model.Address
import com.borayildirim.foodiehub.domain.model.CardType
import com.borayildirim.foodiehub.domain.model.Order
import com.borayildirim.foodiehub.domain.model.OrderItem
import com.borayildirim.foodiehub.domain.model.OrderStatus
import com.borayildirim.foodiehub.domain.model.OrderSummary
import com.borayildirim.foodiehub.domain.model.PaymentCard
import com.borayildirim.foodiehub.domain.usecase.address.GetDefaultAddressUseCase
import com.borayildirim.foodiehub.domain.usecase.address.GetUserAddressesUseCase
import com.borayildirim.foodiehub.domain.usecase.payment.AddPaymentCardUseCase
import com.borayildirim.foodiehub.domain.usecase.cart.ClearCartUseCase
import com.borayildirim.foodiehub.domain.usecase.order.CreateOrderUseCase
import com.borayildirim.foodiehub.domain.usecase.payment.DeletePaymentCardUseCase
import com.borayildirim.foodiehub.domain.usecase.cart.GetCartItemsUseCase
import com.borayildirim.foodiehub.domain.usecase.payment.GetUserCardsUseCase
import com.borayildirim.foodiehub.domain.usecase.payment.SetDefaultCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

/**
 * UI State for Payment Screen
 *
 * @property orderSummary Order summary with subtotal, tax, and delivery fee
 * @property availableCards List of user's saved payment cards
 * @property selectedCardId Currently selected payment card ID
 * @property isProcessing Whether payment is being processed
 * @property paymentSuccess Whether payment was successful
 * @property showAddCardDialog Whether to show add card dialog
 */
data class PaymentUiState(
    val orderSummary: OrderSummary = OrderSummary(0.0, 0.0, 0.0),
    val availableCards: List<PaymentCard> = emptyList(),
    val selectedCardId: String? = null,
    val selectedAddress: Address? = null,
    val userAddresses: List<Address> = emptyList(),
    val isProcessing: Boolean = false,
    val paymentSuccess: Boolean = false,
    val showAddCardDialog: Boolean = false,
    val error: String? = null
)

/**
 * ViewModel for Payment Screen
 *
 * Manages payment flow including:
 * - Loading cart items and calculating order summary
 * - Managing user's payment cards (add, delete, select, set default)
 * - Processing payment and creating order
 * - Clearing cart after successful payment
 *
 * @property getCartItemsUseCase Use case to get cart items
 * @property clearCartUseCase Use case to clear cart
 * @property getUserCardsUseCase Use case to get user's payment cards
 * @property addPaymentCardUseCase Use case to add new payment card
 * @property deletePaymentCardUseCase Use case to delete payment card
 * @property setDefaultCardUseCase Use case to set default payment card
 * @property createOrderUseCase Use case to create order
 * @property userPreferencesManager Manager for user preferences
 */
@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val getUserCardsUseCase: GetUserCardsUseCase,
    private val addPaymentCardUseCase: AddPaymentCardUseCase,
    private val deletePaymentCardUseCase: DeletePaymentCardUseCase,
    private val setDefaultCardUseCase: SetDefaultCardUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val userPreferencesManager: UserPreferencesManager,
    private val getUserAddressesUseCase: GetUserAddressesUseCase,
    private val getDefaultAddressUseCase: GetDefaultAddressUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadPaymentData()
        loadUserAddresses()
    }

    /**
     * Loads payment data including cart items and user's payment cards
     *
     * Launches two separate coroutines:
     * 1. Collects cart items and calculates order summary
     * 2. Collects user's payment cards and selects default card
     */
    private fun loadPaymentData() {
        viewModelScope.launch {
            val userId = userPreferencesManager.getUserId().firstOrNull()

            if (userId != null) {
                // Load cart items and calculate order summary
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

                        _uiState.update { it.copy(orderSummary = orderSummary) }
                    }
                }

                // Load user's payment cards
                launch {
                    getUserCardsUseCase(userId).collect { cards ->
                        _uiState.update {
                            it.copy(
                                availableCards = cards,
                                selectedCardId = cards.firstOrNull { card -> card.isDefault }?.id
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadUserAddresses() {
        viewModelScope.launch {
            try {
                val userId = userPreferencesManager.getUserId().firstOrNull()

                if (userId != null) {
                    // Get all addresses
                    getUserAddressesUseCase(userId).collect { addresses ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                userAddresses = addresses,
                                selectedAddress = addresses.firstOrNull { it.isDefault }
                                    ?: addresses.firstOrNull()
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to load addresses: ${e.message}") }
            }
        }
    }

    /**
     * Selects a payment card
     *
     * @param cardId Payment card ID to select
     */
    fun selectCard(cardId: String) {
        _uiState.value = _uiState.value.copy(selectedCardId = cardId)
    }

    fun selectAddresses(address: Address) {
        _uiState.update { it.copy(selectedAddress = address) }
    }

    /**
     * Shows add card dialog
     */
    fun showAddCardDialog() {
        _uiState.value = _uiState.value.copy(showAddCardDialog = true)
    }

    /**
     * Hides add card dialog
     */
    fun hideAddCardDialog() {
        _uiState.value = _uiState.value.copy(showAddCardDialog = false)
    }

    /**
     * Adds a new payment card
     *
     * @param cardNumber Card number (16 digits)
     * @param cardHolderName Card holder's name
     * @param expiryDate Expiry date in MM/YY format
     * @param cvv Card CVV (3 digits)
     * @param cardType Card type (Visa, Mastercard, American Express)
     * @param setAsDefault Whether to set as default payment method
     */
    fun addPaymentCard(
        cardNumber: String,
        cardHolderName: String,
        expiryDate: String,
        cvv: String,
        cardType: CardType,
        setAsDefault: Boolean
    ) {
        viewModelScope.launch {
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
            }
        }
    }

    /**
     * Processes payment
     *
     * Flow:
     * 1. Simulates 2-second payment processing
     * 2. Creates order from cart items
     * 3. Saves order to database
     * 4. Clears cart
     * 5. Shows success dialog
     *
     * @throws Exception if payment processing fails
     */
    fun processPayment() {
        val currentState = _uiState.value

        // Validation: Payment Card
        if (currentState.selectedCardId == null) {
            _uiState.update { it.copy(error = "Please select a payment card") }
            return
        }

        // Validation: Delivery Address
        if (currentState.selectedAddress == null) {
            _uiState.update { it.copy(error = "Please select a delivery address") }
            return
        }

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isProcessing = true, error = null) }

                // Simulate payment processing
                kotlinx.coroutines.delay(2000)

                val userId = userPreferencesManager.getUserId().firstOrNull()
                    ?: throw IllegalStateException("User not logged in")

                // Get cart items
                val cartItems = getCartItemsUseCase().firstOrNull() ?: emptyList()

                if (cartItems.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            isProcessing = false,
                            error = "Cart is empty"
                        )
                    }
                    return@launch
                }

                // Create order ID first
                val orderId = UUID.randomUUID().toString()

                // Create order with items
                val order = Order(
                    id = orderId,
                    userId = userId,
                    items = cartItems.map { cartItem ->
                        OrderItem(
                            id = UUID.randomUUID().toString(),
                            orderId = orderId,
                            productId = cartItem.food.id,
                            productName = cartItem.food.name,
                            productImage = cartItem.food.imageResource.toString(),
                            quantity = cartItem.quantity,
                            price = cartItem.food.price,
                            spicyLevel = 0f, // TODO: Get from cart customization
                            selectedToppings = emptyList(), // TODO: Get from cart customization
                            selectedSides = emptyList() // TODO: Get from cart customization
                        )
                    },
                    totalAmount = currentState.orderSummary.total,
                    orderDate = System.currentTimeMillis(),
                    deliveryAddress = currentState.selectedAddress.fullAddress,
                    estimatedDeliveryTime = currentState.orderSummary.estimatedDeliveryTime,
                    tax = currentState.orderSummary.tax,
                    deliveryFee = currentState.orderSummary.deliveryFee,
                    status = OrderStatus.PENDING
                )

                // Create order
                createOrderUseCase(order)

                // Clear cart
                clearCartUseCase()

                // Show success
                _uiState.update {
                    it.copy(
                        isProcessing = false,
                        paymentSuccess = true
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isProcessing = false,
                        paymentSuccess = false,
                        error = "Payment failed: ${e.message}"
                    )
                }
            }
        }
    }

    /**
     * Resets payment success state
     */
    fun resetPaymentSuccess() {
        _uiState.value = _uiState.value.copy(paymentSuccess = false)
    }

    /**
     * Masks card number for display
     *
     * Shows first 4 and last 4 digits, masks middle 8 digits
     * Example: 1234567812345678 -> 1234 **** **** 5678
     *
     * @param cardNumber Card number to mask
     * @return Masked card number
     */
    private fun maskCardNumber(cardNumber: String): String {
        val cleaned = cardNumber.replace(" ", "")
        return if (cleaned.length == 16) {
            "${cleaned.substring(0, 4)} **** **** ${cleaned.substring(12)}"
        } else {
            cardNumber
        }
    }

    /*

    TODO: Y.A.G.N.I.

    fun deleteCard(cardId: String) {
    viewModelScope.launch {
        try {
            val userId = userPreferencesManager.getUserId().firstOrNull()
            if (userId != null) {
                deletePaymentCardUseCase(cardId)
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = "Failed to delete card: ${e.message}") }
        }
    }
}
     */
}