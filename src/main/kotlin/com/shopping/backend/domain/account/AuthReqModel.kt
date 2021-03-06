package com.shopping.backend.domain.account

data class AuthReqModel(
    val id: String,
    val name: String?,
    val email: String?,
    val password: String,
    val roleType: String?
)