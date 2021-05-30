package com.mainthrowsexception.moodtrackingapp.database.api

import com.mainthrowsexception.moodtrackingapp.database.model.Entry

interface DbApiContract {
    fun addDbListener(listener: DbListener)

    fun removeDbListener(listener: DbListener)

    fun setEntriesQuery(): DbApiContract

    fun setTagsQuery(): DbApiContract

    fun setTagsByEntryIdQuery(entryId: String): DbApiContract

    fun updateEntries(updateEntries: MutableList<Entry>)

    fun createOrUpdateEntry(entry: Entry)

    fun updateTags(tags: MutableList<String>, entryId: String)

    fun sendQuery()
}