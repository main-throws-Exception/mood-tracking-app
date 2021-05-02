package com.mainthrowsexception.moodtrackingapp.database.model

import com.google.firebase.database.PropertyName

data class Tag (
    val uid: String = "",
    @PropertyName("user_id")
    val userId: String = "",
    val name: String = ""
)
