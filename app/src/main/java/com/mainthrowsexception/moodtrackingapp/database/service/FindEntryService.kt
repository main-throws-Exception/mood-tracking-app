package com.mainthrowsexception.moodtrackingapp.database.service

import com.mainthrowsexception.moodtrackingapp.api.Api
import com.mainthrowsexception.moodtrackingapp.api.dto.EntryDto
import com.mainthrowsexception.moodtrackingapp.database.model.*
import com.mainthrowsexception.moodtrackingapp.database.repo.EntryRepo
import com.mainthrowsexception.moodtrackingapp.util.Generator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class FindEntryService (
    private val entryRepo: EntryRepo,
    private val api: Api,
    private val generator: Generator,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun find(id: Long): ServiceResult {
        return withContext(ioDispatcher) {
            try {
                entryRepo.findById(id)?.let { return@withContext ServiceResult.Success(listOf(it)) }
                    ?: return@withContext fetchFromServer(id)
            } catch (t: Throwable) {
                return@withContext ServiceResult.Failure
            }
        }
    }

    suspend fun findByUserIdAndCreatedBetween(userId: Long, createdLowerBound: Long, createdUpperBound: Long): ServiceResult {
        return withContext(ioDispatcher) {
            try {
                val entriesList = entryRepo.findByUserIdAndCreatedBetween(userId, createdLowerBound, createdUpperBound)
                if (entriesList.isEmpty()) {
                    return@withContext ServiceResult.Success(entriesList)
                } else {
                    return@withContext fetchFromServerByUserIdAndCreatedBetween(userId, createdLowerBound, createdUpperBound)
                }
            } catch (t: Throwable) {
                return@withContext ServiceResult.Failure
            }
        }
    }

    private suspend fun fetchFromServerByUserIdAndCreatedBetween(userId: Long, createdLowerBound: Long, createdUpperBound: Long): ServiceResult {
        delay(generator.timeoutMillis(3)) // emulating http request to server
        val entriesDtoList = api.fetchEntriesByUserIdAndCreatedBetween(userId, createdLowerBound, createdUpperBound) ?: return ServiceResult.NotFound
//        val tagsDtoList = api.fetchTagsByEntryId(id)
//        val tagsList = tagsDtoListToModel(tagsDtoList)
        val entriesList: MutableList<Entry> = ArrayList()
        entriesDtoList.forEach { entryDto -> entriesList.add(entryRepo.add(entryDto.toModel())) }
        return ServiceResult.Success(entriesList)
    }

    private suspend fun fetchFromServer(id: Long): ServiceResult {
        delay(generator.timeoutMillis(3)) // emulating http request to server
        val entryDto = api.fetchEntryById(id) ?: return ServiceResult.NotFound
//        val tagsDtoList = api.fetchTagsByEntryId(id)
//        val tagsList = tagsDtoListToModel(tagsDtoList)
        val entry = entryRepo.add(entryDto.toModel())
        return ServiceResult.Success(listOf(entry))
    }

//    private fun tagsDtoListToModel(tagsDtoList: List<TagDto>): List<Tag> {
//        val tagsList: MutableList<Tag> = ArrayList()
//
//        for (i in 0..10) {
//            tagsList.add(tagsDtoList[i].toModel())
//        }
//
//        return tagsList
//    }

//    private fun TagDto.toModel(): Tag = Tag(
//        TagId (id),
//        name
//    )

    private fun EntryDto.toModel(): Entry = Entry (
        id,
        userId,
        note,
        mood,
        created,
        System.currentTimeMillis()
    )

    fun findAll(): List<Entry> {
        return entryRepo.findAll()
    }
}
