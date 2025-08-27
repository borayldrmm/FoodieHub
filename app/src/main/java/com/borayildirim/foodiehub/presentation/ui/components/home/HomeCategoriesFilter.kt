package com.borayildirim.foodiehub.presentation.ui.components.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.borayildirim.foodiehub.domain.model.Category
import com.borayildirim.foodiehub.presentation.theme.FilterNotSelectedColor
import com.borayildirim.foodiehub.presentation.theme.FoodieHubTheme

@Composable
fun HomeCategoriesFilter(
    categories: List<Category>,
    onCategoryClick: (Int) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) {category ->
            Button(
                onClick = { onCategoryClick(category.id) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (category.isSelected)
                    MaterialTheme.colorScheme.onPrimary
                    else
                    Color.Transparent
                ),
                shape = RoundedCornerShape(24.dp),
                border = if (!category.isSelected)
                    BorderStroke(1.dp, FilterNotSelectedColor)
                else null
            ) {
                Text(
                    text = category.name,
                    color = if (category.isSelected)
                        Color.White
                    else
                        FilterNotSelectedColor
                )
            }
        }
    }
}