package com.mainthrowsexception.moodtrackingapp.api

import com.mainthrowsexception.moodtrackingapp.api.dto.EntryDto
import com.mainthrowsexception.moodtrackingapp.api.dto.TagDto
import com.mainthrowsexception.moodtrackingapp.api.dto.UserDto
import com.mainthrowsexception.moodtrackingapp.entry.model.EntryId
import com.mainthrowsexception.moodtrackingapp.entry.model.UserId
import com.mainthrowsexception.moodtrackingapp.util.Generator

class ApiImpl(private val generator: Generator) : Api {

    override fun fetchEntryById(id: EntryId): EntryDto {
        return EntryDto(
            id.value,
            0,
            generator.string(20),
            generator.nextInt(5),
            System.currentTimeMillis() - generator.nextInt(100) * 1000
        )
    }

    override fun fetchEntriesByUserId(userId: UserId): List<EntryDto>? {
        val entriesList: MutableList<EntryDto> = ArrayList()

        for (i in 0..10) {
            entriesList.add(
                EntryDto(
                generator.nextInt(10000).toLong(),
                userId.value,
                generator.string(20),
                generator.nextInt(5),
                System.currentTimeMillis() - generator.nextInt(100) * 1000
            ))
        }


        return if (entriesList.isEmpty()) {
            null
        } else {
            entriesList
        }
    }

    override fun fetchEntriesByUserIdAndCreatedBetween(
        userId: UserId,
        createdLowerBound: Long,
        createdUpperBound: Long
    ): List<EntryDto>? {
        val entriesList: MutableList<EntryDto> = ArrayList()

        for (i in 0..10) {
            entriesList.add(
                EntryDto(
                    generator.nextInt(10000).toLong(),
                    userId.value,
                    generator.string(20),
                    generator.nextInt(5),
                    createdLowerBound + generator.nextInt((createdUpperBound - createdLowerBound).toInt())
                ))
        }

        return if (entriesList.isEmpty()) {
            null
        } else {
            entriesList
        }
    }

    override fun fetchTagsByEntryId(entryId: EntryId): List<TagDto> {
        val tagsList: MutableList<TagDto> = ArrayList()

        for (i in 0..10) {
            tagsList.add(
                TagDto(
                    generator.nextInt(10000).toLong(),
                    generator.string(10)
                )
            )
        }

        return tagsList
    }

    override fun fetchUserByEmail(email: String): UserDto? {
        return UserDto(
            generator.nextInt(100).toLong(),
            "admin",
            email,
            "admin"
        )
    }
}
