package com.mainthrowsexception.moodtrackingapp.entry.repo

import com.mainthrowsexception.moodtrackingapp.entry.model.Entry
import com.mainthrowsexception.moodtrackingapp.entry.model.EntryId
import com.mainthrowsexception.moodtrackingapp.entry.model.UserId

interface EntryRepo {
    fun findAll(): List<Entry>
    fun findById(id: EntryId): Entry?
    fun findByUserIdAndCreatedBetween(userId: UserId, createdLowerBound: Long, createdUpperBound: Long): List<Entry>
    fun add(entry: Entry): Entry
    fun update(entry: Entry)
    fun removeById(id: EntryId)
}