package com.mainthrowsexception.moodtrackingapp.api

import com.mainthrowsexception.moodtrackingapp.api.dto.EntryDto
import com.mainthrowsexception.moodtrackingapp.entry.model.EntryId
import com.mainthrowsexception.moodtrackingapp.util.Generator

class ApiImpl(private val generator: Generator) : Api {

    override fun fetchEntry(id: EntryId): EntryDto {
        return EntryDto(
            id.value,
            generator.nextInt(4),
            generator.tagsList(5, 3),
            System.currentTimeMillis() - generator.nextInt(100) * 1000
        )
    }
}
