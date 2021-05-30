package com.mainthrowsexception.moodtrackingapp.ui.common.presenter

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.ChartsContract
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.floor

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
                prepareMoodLine(entriesList)
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

    override fun prepareMoodLine(entriesList: ArrayList<Entry>) {
        val moodByDay = FloatArray(7)
        val moodsInDay = IntArray(7)

        for (entry in entriesList) {
            val createdSinceLastWeek = entry.created - (System.currentTimeMillis() - 604_800_000)
            val dayOfWeek = floor(createdSinceLastWeek / 86_400_000f).toInt()
            Log.i("ChartsPrepareMoodLine", "Entry " + entry.uid + " created " + entry.created + " since last week " + createdSinceLastWeek + " day of week " + dayOfWeek)
            moodByDay[dayOfWeek] = moodByDay[dayOfWeek] + entry.mood
            moodsInDay[dayOfWeek] = moodsInDay[dayOfWeek] + 1
        }

        val moodLines = ArrayList<com.github.mikephil.charting.data.Entry>()

        for (i in 0..6) {
            if (moodsInDay[i] == 0) {
                moodLines.add(com.github.mikephil.charting.data.Entry(i.toFloat(), 0f))
            } else {
                moodLines.add(com.github.mikephil.charting.data.Entry(i.toFloat(), moodByDay[i] / moodsInDay[i]))
            }
        }

        view.onMoodLineReady(moodLines)
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
