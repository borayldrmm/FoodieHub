package com.borayildirim.foodiehub.presentation.ui.components.customization

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.domain.model.SideOption
import com.borayildirim.foodiehub.presentation.theme.GrayText
import com.borayildirim.foodiehub.presentation.theme.PrimaryRed

@Composable
fun SideOptionSection(
    title: String,
    sideOptions: List<SideOption>,
    selectedSideOptions: List<SideOption>,
    onSideOptionToggle: (SideOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(sideOptions) {sideOption ->
                SideOptionCard(
                    sideOption = sideOption,
                    isSelected = selectedSideOptions.contains(sideOption),
                    onClick = { onSideOptionToggle(sideOption) }
                )
            }

        }
    }
}

@Composable
private fun SideOptionCard(
    sideOption: SideOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .size(110.dp)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) PrimaryRed else Color.Transparent,
                shape = CardDefaults.shape
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(sideOption.imageRes),
                    contentDescription = sideOption.name,
                    modifier = Modifier.size(55.dp)
                )

                Text(
                    text = sideOption.name,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )

                Text(
                    "${sideOption.price} ₺",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
                    .size(20.dp)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.onPrimary else GrayText,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = if (isSelected) stringResource(R.string.added_txt) else stringResource(R.string.added_txt),
                    tint = Color.White,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}