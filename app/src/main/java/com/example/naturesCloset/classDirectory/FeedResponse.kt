package com.example.naturesCloset.classDirectory

data class FeedResponse(
    val status: String,
    val msg: String,
    val data: ArrayList<User>
)