package com.borayildirim.foodiehub.domain.model

data class Address(
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String
) {
    fun getFullAddress(): String {
        return "$street, $city, $state, $zipCode"
    }
}