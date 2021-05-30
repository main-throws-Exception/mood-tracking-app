package com.mainthrowsexception.moodtrackingapp.database.model.dto

data class UserDto(
    val uid: String = "",
    val username: String = "",
    val email: String = ""
) {
    constructor (copy: UserDto): this(
            uid = copy.uid,
            username = copy.username,
            email = copy.email,
        )
}