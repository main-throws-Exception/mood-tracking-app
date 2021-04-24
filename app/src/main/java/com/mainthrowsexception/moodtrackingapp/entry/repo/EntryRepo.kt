package com.mainthrowsexception.moodtrackingapp.entry.repo

import com.mainthrowsexception.moodtrackingapp.entry.model.Entry
import com.mainthrowsexception.moodtrackingapp.entry.model.EntryId

interface EntryRepo {
    fun findAll(): List<Entry>
    fun findById(id: EntryId): Entry?
    fun add(entry: Entry): Entry
    fun update(entry: Entry)
    fun removeById(id: EntryId)
}