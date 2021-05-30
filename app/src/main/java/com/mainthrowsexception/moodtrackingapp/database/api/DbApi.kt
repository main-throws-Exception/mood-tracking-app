package com.mainthrowsexception.moodtrackingapp.database.api

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import com.mainthrowsexception.moodtrackingapp.database.model.dto.EntryDto
import com.mainthrowsexception.moodtrackingapp.database.model.dto.Repo
import com.mainthrowsexception.moodtrackingapp.database.model.dto.TagDto

class DbApi : DbApiContract, ValueEventListener {

    private val uid = FirebaseAuth.getInstance().currentUser!!.uid

    private val dbRef = Firebase.database(DB_URL).reference

    private val repo: Repo = Repo()

    fun copyRepo(): Repo {
        return Repo(repo)
    }

    private var query: Query? = null

    private lateinit var resultTypeName: ResultTypeName

    private var dbIsBusy = false
        get() { return field }

    private val listeners: MutableList<DbListener> = ArrayList()

    override fun addDbListener(listener: DbListener) {
        listeners.add(listener)
    }

    override fun removeDbListener(listener: DbListener) {
        listeners.remove(listener)
    }

    override fun setEntriesQuery(): DbApiContract {
        query = dbRef.child("entries")
            .child(uid)
        resultTypeName = ResultTypeName.ENTRIES
        return this
    }

    override fun setTagsQuery(): DbApiContract {
        if (dbIsBusy) {
            throw Exception("It is not possible to send a request to the database because it is busy")
        }
        query = dbRef.child("tags")
            .child(uid)
        resultTypeName = ResultTypeName.TAGS
        return this
    }

    override fun setTagsByEntryIdQuery(entryId: String): DbApiContract {
        if (dbIsBusy) {
            throw Exception("It is not possible to send a request to the database because it is busy")
        }
        setTagsQuery()
        query = (query as DatabaseReference).orderByChild("entryId")
            .equalTo(entryId)
        return this
    }

    override fun sendQuery() {
        if (dbIsBusy) {
            throw Exception("It is not possible to send a request to the database because it is busy")
        }
        if (query == null) {
            throw Exception(
                "It is not possible to send a request to the database because the query is not set"
            )
        }
        dbIsBusy = true
        repo.clear()
        query!!.addListenerForSingleValueEvent(this)
    }

    override fun updateEntries(updateEntries: MutableList<Entry>) {
        Log.d("DbApi", "sendAddTagsQuery called")

        updateEntries.forEach { entry ->
            createOrUpdateEntry(entry)
            updateTags(entry.tags, entry.uid)
        }
    }

    override fun createOrUpdateEntry(entry: Entry) {
        setEntriesQuery()
        val entriesRef = query as DatabaseReference
        if (entry.uid.isEmpty()) {
            entry.uid = entriesRef.push().key!!
        }
        entry.userId = this.uid
        entriesRef.child(entry.uid)
            .setValue(EntryDto(entry))
        repo.entries.clear()
    }

    override fun updateTags(tags: MutableList<String>, entryId: String) {
        setTagsQuery()
        addNewTags(tags, entryId)
        removeTags(tags)
        repo.tags.clear()
    }

    private fun addNewTags(tags: MutableList<String>, entryId: String) {
        val repoTagNames = repo.tags.map { it.name }
        val updateTagNames = tags
        val newTagNames = updateTagNames.filter { !repoTagNames.contains(it) }.toMutableList()

        newTagNames.forEach { newTagName ->
            val newTagDto = TagDto(
                uid = dbRef.push().key!!,
                entryId = entryId,
                name = newTagName,
            )
            (query as DatabaseReference).child(newTagDto.uid)
                .setValue(newTagDto)
        }
    }

    private fun removeTags(tags: MutableList<String>) {
        val updateTagNames = tags
        val removeTags = repo.tags.filter { !updateTagNames.contains(it.name) }

        removeTags.forEach { tag ->
            (query as DatabaseReference).child(tag.uid)
                .removeValue()
        }
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        Log.d("DbApi", "onDataChange called")

        snapshot.children.forEach { tag ->
            when (resultTypeName) {
                ResultTypeName.TAGS -> tag.getValue(TagDto::class.java)?.let { repo.tags.add(it) }
            }
        }
        dbIsBusy = false
        query = null
        listeners.forEach { it.onDataReady() }
    }

    override fun onCancelled(error: DatabaseError) {
        Log.d("DbApi", "onCancelled called")
        listeners.forEach { it.onDataFailed() }
        repo.clear()
        query = null
        dbIsBusy = false
    }

    enum class ResultTypeName {
        TAGS, ENTRIES
    }

    companion object {
        const val DB_URL = "https://goodmood-c69a1-default-rtdb.europe-west1.firebasedatabase.app/"
    }
}
