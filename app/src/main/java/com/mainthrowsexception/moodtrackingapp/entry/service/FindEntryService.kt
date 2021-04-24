package com.mainthrowsexception.moodtrackingapp.entry.service

import com.mainthrowsexception.moodtrackingapp.api.Api
import com.mainthrowsexception.moodtrackingapp.api.dto.EntryDto
import com.mainthrowsexception.moodtrackingapp.entry.model.Entry
import com.mainthrowsexception.moodtrackingapp.entry.model.EntryId
import com.mainthrowsexception.moodtrackingapp.entry.repo.EntryRepo
import com.mainthrowsexception.moodtrackingapp.util.Generator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.*

class FindEntryService (
    private val entryRepo: EntryRepo,
    private val api: Api,
    private val generator: Generator,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun find(id: EntryId): ServiceResult {
        return withContext(ioDispatcher) {
            try {
                entryRepo.findById(id)?.let { return@withContext ServiceResult.Success(it) }
                    ?: return@withContext fetchFromServer(id)
            } catch (t: Throwable) {
                return@withContext ServiceResult.Failure
            }
        }
    }

    private suspend fun fetchFromServer(id: EntryId): ServiceResult {
        delay(generator.timeoutMillis(3)) // emulating http request to server
        val entryDto = api.fetchEntry(id) ?: return ServiceResult.NotFound
        val entry = entryRepo.add(entryDto.toModel())
        return ServiceResult.Success(entry)
    }

    private fun EntryDto.toModel(): Entry = Entry (
        EntryId (id),
        mood,
        tags,
        created,
        System.currentTimeMillis()
    )

    fun findAll(): List<Entry> {
        return entryRepo.findAll()
    }
}
