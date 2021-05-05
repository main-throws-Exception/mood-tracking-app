package com.mainthrowsexception.moodtrackingapp.database.model

data class Entry (
    val uid: String = "",
    val userId: String = "0",
    val note: String = "",
    val tags: ArrayList<String> = ArrayList<String>(),
    val mood: Int = -1,
    val created: Long = System.currentTimeMillis()
)
