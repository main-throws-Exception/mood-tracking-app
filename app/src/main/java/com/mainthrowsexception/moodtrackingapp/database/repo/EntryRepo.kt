package com.mainthrowsexception.moodtrackingapp.database.repo

import com.mainthrowsexception.moodtrackingapp.database.model.Entry

interface EntryRepo {
    fun findAll(): List<Entry>
    fun findById(id: Long): Entry?
    fun findByUserIdAndCreatedBetween(userId: Long, createdLowerBound: Long, createdUpperBound: Long): List<Entry>
    fun add(entry: Entry): Entry
    fun update(entry: Entry)
    fun removeById(id: Long)
}