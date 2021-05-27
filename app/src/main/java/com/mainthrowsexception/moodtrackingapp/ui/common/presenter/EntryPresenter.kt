package com.mainthrowsexception.moodtrackingapp.ui.common.presenter

import android.util.Log
import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.EntryContract
import com.mainthrowsexception.moodtrackingapp.database.api.DbApi
import com.mainthrowsexception.moodtrackingapp.database.api.DbListener

class EntryPresenter(
    private var view: EntryContract.View
) : EntryContract.Presenter, DbListener {

    private var db = DbApi()
    private var entry = Entry(mood = 0)

    constructor(view: EntryContract.View, entry: Entry) : this(view) {
        this.entry = entry
    }

    override fun bindView() {
        db.addDbListener(this)
        if (entry.uid.isEmpty()) {
            view.bindData(entry)
            return
        }
        db.setTagsByEntryIdQuery(entry.uid)
            .sendQuery()
    }

    override fun saveChanges(saveEntry: Entry) {
        db.setEntriesQuery()
            .updateEntries(mutableListOf(saveEntry))
    }

    override fun onDataReady() {
        Log.d("EntryPresenter", "onDataReady called")

        val tagsDto = db.copyRepo().tags
        if (tagsDto.isEmpty()) {
            view.bindData(entry)
            return
        }
        val tagNames = tagsDto.map { it.name }.toMutableList()

        entry = Entry(
            uid = entry.uid,
            userId = entry.userId,
            tags = tagNames,
            note = entry.note,
            mood = entry.mood,
            created = entry.created
        )
        view.bindData(entry)
    }

    override fun onDataFailed() {
        Log.d("EntryPresenter", "onDataFailed called")
    }
}
