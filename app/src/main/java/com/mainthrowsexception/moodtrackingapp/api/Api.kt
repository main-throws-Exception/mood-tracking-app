package com.mainthrowsexception.moodtrackingapp.api

import com.mainthrowsexception.moodtrackingapp.api.dto.EntryDto
import com.mainthrowsexception.moodtrackingapp.entry.model.EntryId

interface Api {
    fun fetchEntry(id: EntryId): EntryDto?
}