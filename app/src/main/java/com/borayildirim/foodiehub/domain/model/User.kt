package com.borayildirim.foodiehub.domain.model

data class User(
    val userId : String,
    val name : String,
    val email : String,
    val profilePicture : String? = null,
    val isLogin : Boolean,
    val deliveryAdress: String? = null,
    val phoneNumber: String? = null
)