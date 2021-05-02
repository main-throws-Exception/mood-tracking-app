package com.mainthrowsexception.moodtrackingapp.database.model

import com.google.firebase.database.PropertyName

data class Entry (
    val uid: String = "",
    val userId: String = "0",
    val note: String = "",
    val mood: Int = -1,
    val created: Long = System.currentTimeMillis()
)
