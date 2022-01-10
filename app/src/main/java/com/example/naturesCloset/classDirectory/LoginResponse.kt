package com.example.naturesCloset.classDirectory

import org.json.JSONArray
import java.util.*

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
    val code: String,
    val msg: String,
    val data: Any
)