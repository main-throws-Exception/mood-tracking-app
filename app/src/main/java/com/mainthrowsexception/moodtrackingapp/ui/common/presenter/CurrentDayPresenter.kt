package com.mainthrowsexception.moodtrackingapp.ui.common.presenter

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.CurrentDayContract
import java.time.ZonedDateTime

class CurrentDayPresenter(private val view: CurrentDayContract.View) : CurrentDayContract.Presenter {

    private val userId = FirebaseAuth.getInstance().currentUser!!.uid
    private val databaseRef = Firebase.database("https://goodmood-c69a1-default-rtdb.europe-west1.firebasedatabase.app/").reference

    override fun getEntries() {
        Log.i("CurrentDayPresenter", "getEntries() called")

        val nowZoned: ZonedDateTime = ZonedDateTime.now()
        val midnight: Long = nowZoned.toLocalDate().atStartOfDay(nowZoned.zone).toInstant().toEpochMilli()
        val now = System.currentTimeMillis()

        val query = databaseRef.child("entries")
            .child(userId)
            .orderByChild("created")
            .startAt(midnight.toDouble())
            .endAt(now.toDouble())

        val entries = ArrayList<Entry>()

        query.addValueEventListener(object: ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                Log.e("CurrentDayPresenter", "Failed to read entries from DB:" + error.message)
//                Toast.makeText(view.context, R.string.get_entries_fail, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                Log.i("CurrentDayPresenter", "onDataChange called")

                entries.clear()

                for (item in snapshot.children) {
                    val entry = item.getValue(Entry::class.java)
                    entries.add(entry!!)
                }

                view.onEntriesRead(entries)
//                recyclerView.adapter = EntriesAdapter(entries)
            }
        })
        view.onCurrentDayReady()
    }
}
