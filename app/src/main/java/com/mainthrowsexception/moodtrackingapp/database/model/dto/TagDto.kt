package com.mainthrowsexception.moodtrackingapp.database.model.dto

data class TagDto(
    val uid: String = "",
    val entryId: String = "",
    val name: String = ""
) {
    constructor (copy: TagDto): this(
            uid = copy.uid,
            entryId = copy.entryId,
            name = copy.name,
        )
}
