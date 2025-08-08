package com.borayildirim.foodiehub.domain.model

data class User(
    val userId : String,
    val name : String,
    val email : String,
    val profilePicture : String?,
    val isLogin : Boolean
)