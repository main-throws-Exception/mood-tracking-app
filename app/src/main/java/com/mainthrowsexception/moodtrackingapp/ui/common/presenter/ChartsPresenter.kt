package com.mainthrowsexception.moodtrackingapp.ui.common.presenter

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.ChartsContract
import java.time.LocalDateTime

class ChartsPresenter(private val view: ChartsContract.View) : ChartsContract.Presenter {

    private val userId = FirebaseAuth.getInstance().currentUser!!.uid
    private val databaseRef = Firebase.database("https://goodmood-c69a1-default-rtdb.europe-west1.firebasedatabase.app/").reference
    private lateinit var currentValueEventListener: ValueEventListener
    private lateinit var currentQuery: Query

    private val entriesList: ArrayList<Entry> = ArrayList()

    override fun getData() {
        currentValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                entriesList.clear()
                for (entrySnapshot in snapshot.children) {
                    val entry = entrySnapshot.getValue(Entry::class.java)
                    entriesList.add(entry!!)
                }

                prepareMoods(entriesList)
                prepareTags(entriesList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

        val weekAgo = System.currentTimeMillis() - 604800000

        currentQuery = databaseRef.child("entries").child(userId)
            .orderByChild("created")
            .startAt(weekAgo.toDouble())

        currentQuery.addValueEventListener(currentValueEventListener)
    }

    override fun prepareMoods(entriesList: ArrayList<Entry>) {
        val moods = ArrayList<Float>()
        moods.add(0f)
        moods.add(0f)
        moods.add(0f)
        moods.add(0f)
        moods.add(0f)

        for (entry in entriesList) {
            val mood = entry.mood
            moods[mood] += 1f
        }

        view.onMoodsReady(moods)
    }

    override fun prepareTags(entriesList: ArrayList<Entry>) {
        val tagsMap = HashMap<String, Int>()
        for (entry in entriesList) {
            for (tag in entry.tags) {
                val count = tagsMap.get(tag)
                if (count != null) {
                    tagsMap.put(tag, count + 1)
                } else {
                    tagsMap.put(tag, 1)
                }
            }
        }
        view.onTagsReady(tagsMap)
    }
}
