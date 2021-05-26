package com.mainthrowsexception.moodtrackingapp.database.model

data class Entry (
    var uid: String = "",
    var userId: String = "0",
    var note: String = "",
    var tags: MutableList<String> = ArrayList(),
    var mood: Int = -1,
    var created: Long = System.currentTimeMillis()
)
