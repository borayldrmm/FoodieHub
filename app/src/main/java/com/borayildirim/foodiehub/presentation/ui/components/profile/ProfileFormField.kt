package com.borayildirim.foodiehub.presentation.ui.components.profile

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun ProfileFormField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    enabled: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: (@Composable () -> Unit)? = null
) {
    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            visualTransformation = visualTransformation,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = trailingIcon,
            textStyle = LocalTextStyle.current.copy(
                fontWeight = FontWeight.Medium
            ),
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color.LightGray,
                disabledTextColor = Color.Black,
                focusedTextColor = Color.DarkGray,
                unfocusedTextColor = Color.DarkGray,
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray
            ),

        )
    }
}