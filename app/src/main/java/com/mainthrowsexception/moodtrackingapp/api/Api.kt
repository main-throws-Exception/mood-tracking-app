package com.mainthrowsexception.moodtrackingapp.api

import com.mainthrowsexception.moodtrackingapp.api.dto.EntryDto
import com.mainthrowsexception.moodtrackingapp.api.dto.TagDto
import com.mainthrowsexception.moodtrackingapp.api.dto.UserDto
import com.mainthrowsexception.moodtrackingapp.entry.model.EntryId
import com.mainthrowsexception.moodtrackingapp.entry.model.UserId

interface Api {
    fun fetchEntryById(id: EntryId): EntryDto?
    fun fetchEntriesByUserId(userId: UserId): List<EntryDto>?
    fun fetchEntriesByUserIdAndCreatedBetween(userId: UserId, createdLowerBound: Long, createdUpperBound: Long): List<EntryDto>?
    fun fetchTagsByEntryId(entryId: EntryId): List<TagDto>
    fun fetchUserByEmail(email: String): UserDto?
}
