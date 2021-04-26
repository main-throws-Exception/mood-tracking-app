package com.mainthrowsexception.moodtrackingapp.entry.model

data class User (
    val id: UserId,
    val name: String,
    val email: String,
    val password: String
)
