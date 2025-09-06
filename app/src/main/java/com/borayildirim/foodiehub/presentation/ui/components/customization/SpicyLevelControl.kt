package com.borayildirim.foodiehub.presentation.ui.components.customization

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.borayildirim.foodiehub.R

@Composable
fun SpicyLevelControl(
    spicyLevel: Float,
    onSpicyLevelChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = stringResource(R.string.spicy_txt),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp),
            fontWeight = FontWeight.Bold
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "-",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )

            Slider(
                value = spicyLevel,
                onValueChange = onSpicyLevelChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.onPrimary,
                    activeTrackColor = MaterialTheme.colorScheme.onPrimary
                )
            )

            Text(
                text = "+",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }
    }
}