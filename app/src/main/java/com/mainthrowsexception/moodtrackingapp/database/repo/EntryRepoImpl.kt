package com.mainthrowsexception.moodtrackingapp.database.repo

import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import java.util.concurrent.ConcurrentHashMap

class EntryRepoImpl : EntryRepo {

    private val items: MutableMap<Long, Entry> = ConcurrentHashMap()

    override fun findAll(): List<Entry> {
        return ArrayList(items.values)
    }

    override fun findById(id: Long): Entry? {
        return items[id]
    }

    override fun findByUserIdAndCreatedBetween(
        userId: Long,
        createdLowerBound: Long,
        createdUpperBound: Long
    ): List<Entry> {
        return items.values.filter { entry: Entry -> entry.userId == userId && entry.created >= createdLowerBound && entry.created < createdUpperBound }
    }

    override fun add(entry: Entry): Entry {
        items[entry.id] = entry
        return entry
    }

    override fun update(entry: Entry) {
        items[entry.id] = entry
    }

    override fun removeById(id: Long) {
        items.remove(id)
    }
}