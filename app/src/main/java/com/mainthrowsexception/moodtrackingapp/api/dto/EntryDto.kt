package com.mainthrowsexception.moodtrackingapp.api.dto

data class EntryDto (
    val id: Long,
    val userId: Long,
    val note: String,
    val mood: Int,
    val created: Long
)
