package com.mainthrowsexception.moodtrackingapp.api

import com.mainthrowsexception.moodtrackingapp.api.dto.EntryDto
import com.mainthrowsexception.moodtrackingapp.api.dto.TagDto
import com.mainthrowsexception.moodtrackingapp.api.dto.UserDto

interface Api {
    fun fetchEntryById(id: Long): EntryDto?
    fun fetchEntriesByUserId(userId: Long): List<EntryDto>?
    fun fetchEntriesByUserIdAndCreatedBetween(userId: Long, createdLowerBound: Long, createdUpperBound: Long): List<EntryDto>?
    fun fetchTagsByEntryId(entryId: Long): List<TagDto>
    fun fetchUserByEmail(email: String): UserDto?
}
