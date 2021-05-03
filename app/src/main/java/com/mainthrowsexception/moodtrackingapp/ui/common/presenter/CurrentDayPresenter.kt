package com.mainthrowsexception.moodtrackingapp.ui.common.presenter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.CurrentDayContract
import com.mainthrowsexception.moodtrackingapp.ui.currentday.EntriesAdapter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime

class CurrentDayPresenter(private val view: CurrentDayContract.View,
                          private val recyclerView: RecyclerView) : CurrentDayContract.Presenter {

    override fun getEntries() {
        Log.i("CurrentDayPresenter", "getEntries() called")

        val nowZoned: ZonedDateTime = ZonedDateTime.now()
        val midnight: Long = nowZoned.toLocalDate().atStartOfDay(nowZoned.zone).toInstant().toEpochMilli()
//        Log.i("MIDNIGHT", midnight.toString())

        val now = System.currentTimeMillis()
//        Log.i("NOW", now.toString())

        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val databaseRef = Firebase.database("https://goodmood-c69a1-default-rtdb.europe-west1.firebasedatabase.app/").reference
            .child("entries")
            .child(userId)
            .orderByChild("created")
            .startAt(midnight.toDouble())
            .endAt(now.toDouble())

        val entries = ArrayList<Entry>()

        databaseRef.addValueEventListener(object: ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                Log.i("CurrentDayPresenter", "onDataChange called")

                for (item in snapshot.children) {
                    val entry = item.getValue(Entry::class.java)
                    entries.add(entry!!)
                }
                recyclerView.adapter = EntriesAdapter(entries)
            }
        })
    }

//        val dateTimeNowMidnight = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
//        val dateTimeNow = LocalDateTime.now()

//        return databaseRef.child("entries").child(userId)
//            .orderByChild("created")
//            .startAt(dateTimeNowMidnight.withDayOfMonth(1).atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()?.toDouble()!!)
//            .endAt(dateTimeNow.withDayOfMonth(1).atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()?.toDouble()!!)
}
