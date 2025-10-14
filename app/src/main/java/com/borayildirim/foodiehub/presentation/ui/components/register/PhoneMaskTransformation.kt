package com.borayildirim.foodiehub.presentation.ui.components.register

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.lang.StringBuilder

class PhoneMaskTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text.filter { it.isDigit() }.take(10)

        val builder = StringBuilder()
        builder.append("0 (")

        // First 3 digits
        for (i in 0 until 3) {
            if (i < digits.length) {
                builder.append(digits[i])
            } else {
                builder.append("_")
            }
        }

        builder.append(") ")

        // Next 3 digits
        for (i in 3 until 6) {
            if (i < digits.length) {
                builder.append(digits[i])
            } else {
                builder.append("_")
            }
        }

        builder.append(" ")

        // Next 2 digits
        for (i in 6 until 8) {
            if (i < digits.length) {
                builder.append(digits[i])
            } else {
                builder.append("_")
            }
        }

        builder.append(" ")

        // Last 2 digits
        for (i in 8 until 10) {
            if (i < digits.length) {
                builder.append(digits[i])
            } else {
                builder.append("_")
            }
        }

        val transformedText = builder.toString()

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset == 0) return 0
                if (offset > digits.length) return transformedText.length

                return when {
                    offset <= 3 -> offset + 3
                    offset <= 6 -> offset + 5
                    offset <= 8 -> offset + 6
                    else -> offset + 7
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset == 0) return 0
                if (digits.isEmpty()) return 0

                return when {
                    offset <= 3 -> 0
                    offset <= 6 -> minOf(offset - 3, digits.length)
                    offset <= 8 -> minOf(3, digits.length)
                    offset <= 11 -> minOf(offset - 5, digits.length)
                    offset <= 12 -> minOf(6, digits.length)
                    offset <= 14 -> minOf(offset - 6, digits.length)
                    offset <= 15 -> minOf(8, digits.length)
                    else -> minOf(offset - 7, digits.length)
                }.coerceIn(0, digits.length)
            }
        }

        return TransformedText(
            AnnotatedString(transformedText),
            offsetMapping
        )
    }
}