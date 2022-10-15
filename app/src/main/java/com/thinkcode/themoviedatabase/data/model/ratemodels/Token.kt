package com.thinkcode.themoviedatabase.data.model.ratemodels

data class Token(
    val expires_at: String,
    val request_token: String,
    val success: Boolean
)