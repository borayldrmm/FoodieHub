package com.borayildirim.foodiehub.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.presentation.ui.components.payment.*
import com.borayildirim.foodiehub.presentation.viewmodels.PaymentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    navController: NavController,
    viewModel: PaymentViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddressDialog by remember { mutableStateOf(false) }

    // Payment Success Dialog
    if (uiState.paymentSuccess) {
        PaymentSuccessDialog(
            onDismiss = {
                viewModel.resetPaymentSuccess()
                navController.navigate("home") {
                    popUpTo("home") { inclusive = false }
                }
            }
        )
    }

    // Add Card Dialog
    if (uiState.showAddCardDialog) {
        AddPaymentCardDialog(
            onDismiss = { viewModel.hideAddCardDialog() },
            onAddCard = { cardNumber, holderName, expiryDate, cvv, cardType, setAsDefault ->
                viewModel.addPaymentCard(
                    cardNumber, holderName, expiryDate, cvv, cardType, setAsDefault
                )
            }
        )
    }

    // Address Selection Dialog
    if (showAddressDialog) {
        AddressSelectionDialog(
            addresses = uiState.userAddresses,
            selectedAddress = uiState.selectedAddress,
            onAddressSelected = { address ->
                viewModel.selectAddresses(address)
            },
            onDismiss = { showAddressDialog = false },
            onAddAddressClick = {
                showAddressDialog = false
                navController.navigate("add_address")
            }
        )
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.payment),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_txt)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Order Sumary
                item {
                    OrderSummaryCard(
                        subtotal = uiState.orderSummary.subtotal,
                        tax = uiState.orderSummary.tax,
                        deliveryFee = uiState.orderSummary.deliveryFee,
                        total = uiState.orderSummary.total,
                        estimatedTime = uiState.orderSummary.estimatedDeliveryTime
                    )
                }

                // Delivery Address Card
                item {
                    DeliveryAddressCard(
                        selectedAddress = uiState.selectedAddress,
                        onChangeAddressClick = { showAddressDialog = true }
                    )
                }

                // Payment Methods Header
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.payment_methods),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(onClick = { viewModel.showAddCardDialog() }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(R.string.add_card_button_desc),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }

                // Payment Cards
                items(uiState.availableCards) { card ->
                    PaymentCardItem(
                        card = card,
                        isSelected = card.id == uiState.selectedCardId,
                        onClick = { viewModel.selectCard(card.id) }
                    )
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }

            // Error Snackbar
            uiState.error?.let { errorMessage ->
                LaunchedEffect(errorMessage) {
                    // TODO: Show error (you can use SnackbarHost if needed)
                }
            }

            // Bottom Payment Bar
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = stringResource(R.string.total_price),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "₺${String.format("%.2f", uiState.orderSummary.total)}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Button(
                        onClick = { viewModel.processPayment() },
                        enabled = !uiState.isProcessing &&
                                   uiState.selectedCardId != null &&
                                   uiState.selectedAddress != null,
                        modifier = Modifier
                            .height(56.dp)
                            .width(160.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        if (uiState.isProcessing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White
                            )
                        } else {
                            Text(
                                text = stringResource(R.string.pay_now),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                fontSize = 19.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}