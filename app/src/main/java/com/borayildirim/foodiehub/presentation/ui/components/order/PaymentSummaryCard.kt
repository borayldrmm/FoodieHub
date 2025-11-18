package com.borayildirim.foodiehub.presentation.ui.components.order

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.domain.model.Order

@Composable
fun PaymentSummaryCard(order: Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF5F5)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xFFFFE5E5)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.payment_summary),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFFFFCCCC)
            )

            // Subtotal
            PaymentRow(
                label = stringResource(R.string.subtotal),
                amount = order.totalAmount - order.tax - order.deliveryFee,
                textColor = Color(0xFF374151)
            )

            // Tax
            PaymentRow(
                label = stringResource(R.string.tax),
                amount = order.tax,
                textColor = Color(0xFF374151)
            )

            // Delivery Fee
            PaymentRow(
                label = stringResource(R.string.delivery_fee),
                amount = order.deliveryFee,
                textColor = Color(0xFF374151)
            )

            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )

            // Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.total),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
                Text(
                    text = "₺ ${String.format("%.2f", order.totalAmount)}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
private fun PaymentRow(
    label: String,
    amount: Double,
    textColor: Color = Color(0xFF6B7280)
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor
        )
        Text(
            text = "₺ ${String.format("%.2f", amount)}",
            style = MaterialTheme.typography.bodyLarge,
            color = textColor
        )
    }
}