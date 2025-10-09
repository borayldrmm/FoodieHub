package com.borayildirim.foodiehub.domain.model

data class User(
    val userId : String,
    val fullName : String,
    val email : String,
    val profilePicture : String? = null,
    val deliveryAddress: String? = null,
    val phoneNumber: String,
    val password: String
)