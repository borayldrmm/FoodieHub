package com.borayildirim.foodiehub.domain.model

sealed class AuthException(message: String) : Exception(message) {
    object UserNotFound : AuthException("USER_NOT_FOUND")
    object WrongPassword : AuthException("WRONG_PASSWORD")
    object EmailAlreadyExists : AuthException("EMAIL_ALREADY_EXISTS")
}