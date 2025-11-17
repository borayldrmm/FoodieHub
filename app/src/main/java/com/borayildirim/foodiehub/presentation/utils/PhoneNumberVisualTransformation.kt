package com.borayildirim.foodiehub.presentation.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text.filter { it.isDigit() }.take(10)

        val builder = StringBuilder()
        builder.append("0 (")

        for (i in 0 until 3) {
            if (i < digits.length) {
                builder.append(digits[i])
            } else {
                builder.append("_")
            }
        }

        builder.append(") ")

        for (i in 3 until 6) {
            if (i < digits.length) {
                builder.append(digits[i])
            } else {
                builder.append("_")
            }
        }

        builder.append(" ")

        for (i in 6 until 8) {
            if (i < digits.length) {
                builder.append(digits[i])
            } else {
                builder.append("_")
            }
        }

        builder.append(" ")

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
                if (digits.isEmpty()) return 0

                return when {
                    offset <= 3 -> offset + 3
                    offset <= 6 -> offset + 5
                    offset <= 8 -> offset + 6
                    else -> offset + 7
                }.coerceIn(0, transformedText.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset == 0) return 0
                if (digits.isEmpty()) return 0

                return when {
                    offset <= 3 -> 0
                    offset < 3 + 3 -> minOf(offset - 3, digits.length)
                    offset < 3 + 3 + 2 -> minOf(3, digits.length)
                    offset < 3 + 3 + 2 + 3 -> minOf(offset - 5, digits.length)
                    offset < 3 + 3 + 2 + 3 + 1 -> minOf(6, digits.length)
                    offset < 3 + 3 + 2 + 3 + 1 + 2 -> minOf(offset - 6, digits.length)
                    offset < 3 + 3 + 2 + 3 + 1 + 2 + 1 -> minOf(8, digits.length)
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