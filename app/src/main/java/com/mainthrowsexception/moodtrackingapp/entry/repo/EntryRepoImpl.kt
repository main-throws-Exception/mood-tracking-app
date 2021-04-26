package com.mainthrowsexception.moodtrackingapp.entry.repo

import com.mainthrowsexception.moodtrackingapp.api.dto.EntryDto
import com.mainthrowsexception.moodtrackingapp.entry.model.Entry
import com.mainthrowsexception.moodtrackingapp.entry.model.EntryId
import com.mainthrowsexception.moodtrackingapp.entry.model.UserId
import java.util.concurrent.ConcurrentHashMap

class EntryRepoImpl : EntryRepo {

    private val items: MutableMap<EntryId, Entry> = ConcurrentHashMap()

    override fun findAll(): List<Entry> {
        return ArrayList(items.values)
    }

    override fun findById(id: EntryId): Entry? {
        return items[id]
    }

    override fun findByUserIdAndCreatedBetween(
        userId: UserId,
        createdLowerBound: Long,
        createdUpperBound: Long
    ): List<Entry> {
        return items.values.filter { entry: Entry -> entry.userId.value == userId.value && entry.created >= createdLowerBound && entry.created < createdUpperBound }
    }

    override fun add(entry: Entry): Entry {
        items[entry.id] = entry
        return entry
    }

    override fun update(entry: Entry) {
        items[entry.id] = entry
    }

    override fun removeById(id: EntryId) {
        items.remove(id)
    }
}