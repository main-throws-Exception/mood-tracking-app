package com.mainthrowsexception.moodtrackingapp.entry.model

data class Entry (
    val id: EntryId,
    val mood: Int,
    val tags: List<String>,
    val created: Long,
    val updated: Long
)
