package com.mainthrowsexception.moodtrackingapp.api.dto

import java.util.*

data class EntryDto (
    val id: Long,
    val mood: Int,
    val tags: List<String>,
    val created: Long
)