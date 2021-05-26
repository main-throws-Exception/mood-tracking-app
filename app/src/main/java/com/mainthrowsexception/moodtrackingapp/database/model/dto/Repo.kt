package com.mainthrowsexception.moodtrackingapp.database.model.dto

import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import com.mainthrowsexception.moodtrackingapp.database.model.Tag
import com.mainthrowsexception.moodtrackingapp.database.model.User

data class Repo(
    val entries: MutableList<EntryDto> = ArrayList(),
    val tags: MutableList<TagDto> = ArrayList(),
    val users: MutableList<UserDto> = ArrayList(),
) {
    fun clear() {
        entries.clear()
        tags.clear()
        users.clear()
    }

    constructor (copy: Repo): this(
        entries = copy.entries.map { EntryDto(it) }.toMutableList(),
        tags = copy.tags.map { TagDto(it) }.toMutableList(),
        users = copy.users.map { UserDto(it) }.toMutableList(),
    )
}
