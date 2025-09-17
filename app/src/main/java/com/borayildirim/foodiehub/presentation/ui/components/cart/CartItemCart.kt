package com.borayildirim.foodiehub.presentation.ui.components.cart

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.domain.model.CartItem

@SuppressLint("DefaultLocale")
@Composable
fun CartItemCart(
    cartItem: CartItem,
    onQuantityChange: (Int) -> Unit,
    onRemoveItem: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 9.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 13.dp, end = 13.dp, top = 3.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Food Image
            Image(
                painter = painterResource(cartItem.food.imageResource),
                contentDescription = cartItem.food.name,
                modifier = Modifier.size(75.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Food Info + Quantity Controls
            Column(modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cartItem.food.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 10.dp, top = 12.dp)
                )

                Text(
                    text = "₺${String.format("%.2f", cartItem.food.price)}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 10.dp, top = 6.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Quantity Controls
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if (cartItem.quantity > 1) {
                                onQuantityChange(cartItem.quantity - 1)
                            }
                        }
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = stringResource(R.string.lower_txt))
                    }

                    Text(
                        text = cartItem.quantity.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    IconButton(
                        onClick = { onQuantityChange(cartItem.quantity + 1) }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = stringResource(R.string.higher_txt))
                    }
                }
            }

            IconButton(
                onClick = onRemoveItem
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = stringResource(R.string.remove_txt),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}