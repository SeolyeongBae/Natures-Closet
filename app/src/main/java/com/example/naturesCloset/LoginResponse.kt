package com.example.naturesCloset

/*
data class LoginResponse(
    val email: Int,
    val id: Int,
    val name: String,
    val password: String,
    val phonenum: String,
    val seq: String,
    val success: Boolean
)

*/

data class LoginResponse(
    val status: String,
    val msg: String,
    val data: String
)