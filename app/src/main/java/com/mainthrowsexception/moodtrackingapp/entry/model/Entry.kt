package com.mainthrowsexception.moodtrackingapp.entry.model

data class Entry (
    val id: EntryId,
    val userId: UserId,
    val note: String,
    val mood: Int,
    val created: Long,
    val updated: Long
)
