package com.borayildirim.foodiehub.presentation.ui.components.order

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.domain.model.Order
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrderInfoCard(order: Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xFFE5E5E5)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Order ID
            OrderInfoRow(
                label = stringResource(R.string.order_id),
                value = "#${order.id.take(8)}"
            )

            HorizontalDivider(color = Color(0xFFF5F5F5))

            // Order Date
            OrderInfoRow(
                label = stringResource(R.string.order_date),
                value = formatDate(order.orderDate)
            )

            HorizontalDivider(color = Color(0xFFF5F5F5))

            // Delivery Address
            OrderInfoRow(
                label = stringResource(R.string.delivery_address),
                value = order.deliveryAddress
            )

            HorizontalDivider(color = Color(0xFFF5F5F5))

            // Estimated Delivery
            OrderInfoRow(
                label = stringResource(R.string.estimated_delivery),
                value = order.estimatedDeliveryTime
            )
        }
    }
}

@Composable
private fun OrderInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f, fill = false),
            textAlign = androidx.compose.ui.text.style.TextAlign.End
        )
    }
}

/**
 * Formats timestamp to readable date
 * Example: "31 Oca 2025, 14:30"
 */
private fun formatDate(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("tr"))
    return dateFormat.format(Date(timestamp))
}