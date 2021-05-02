package com.mainthrowsexception.moodtrackingapp.ui.common.presenter

import android.text.format.DateUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.CalendarContract
import org.naishadhparmar.zcustomcalendar.CustomCalendar
import org.naishadhparmar.zcustomcalendar.Property
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.TemporalAdjusters
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.reflect.typeOf

class CalendarPresenter(view: CalendarContract.View) : CalendarContract.Presenter {

    private var view: CalendarContract.View? = view
    private val entriesList: TreeMap<Int, MutableList<Entry>> = TreeMap<Int, MutableList<Entry>>()
    private val userId = FirebaseAuth.getInstance().currentUser!!.uid
    private val databaseRef = Firebase.database("https://goodmood-c69a1-default-rtdb.europe-west1.firebasedatabase.app/").reference

    override fun initCalendar(calendar: CustomCalendar) {
        // Default
        val descHashMap: HashMap<Any, Property> = HashMap()
        val defaultProperty = Property()

        defaultProperty.layoutResource = R.layout.calendar_default_view
        defaultProperty.dateTextViewResource = R.id.calendar_default_view__text

        descHashMap["default"] = defaultProperty

        // Current day
        val currentProperty = Property()
        currentProperty.layoutResource = R.layout.calendar_current_view
        currentProperty.dateTextViewResource = R.id.calendar_current_view__text

        descHashMap["current"] = currentProperty

        // Very bad day
        val veryBadProperty = Property()
        veryBadProperty.layoutResource = R.layout.calendar_very_bad_view
        veryBadProperty.dateTextViewResource = R.id.calendar_very_bad_view__text

        descHashMap["veryBad"] = veryBadProperty

        // Bad day
        val badProperty = Property()
        badProperty.layoutResource = R.layout.calendar_bad_view
        badProperty.dateTextViewResource = R.id.calendar_bad_view__text

        descHashMap["bad"] = badProperty

        // Neutral day
        val neutralProperty = Property()
        neutralProperty.layoutResource = R.layout.calendar_neutral_view
        neutralProperty.dateTextViewResource = R.id.calendar_neutral_view__text

        descHashMap["neutral"] = neutralProperty

        // Good day
        val goodProperty = Property()
        goodProperty.layoutResource = R.layout.calendar_good_view
        goodProperty.dateTextViewResource = R.id.calendar_good_view__text

        descHashMap["good"] = goodProperty

        // Very good day
        val veryGoodProperty = Property()
        veryGoodProperty.layoutResource = R.layout.calendar_very_good_view
        veryGoodProperty.dateTextViewResource = R.id.calendar_very_good_view__text

        descHashMap["veryGood"] = veryGoodProperty

        calendar.setMapDescToProp(descHashMap)

        val dateHashMap: HashMap<Int, Any> = HashMap()
        val cal: Calendar = Calendar.getInstance()

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (entrySnapshot in snapshot.children) {
                    val entry = entrySnapshot.getValue(Entry::class.java)
                    val dayOfMonth = LocalDateTime.ofInstant(Instant.ofEpochMilli(entry!!.created), ZoneOffset.UTC).dayOfMonth

                    if (entriesList[dayOfMonth] == null) {
                        entriesList[dayOfMonth] = ArrayList(listOf(entry))
                    } else {
                        entriesList[dayOfMonth]?.add(entry)
                    }
                }

                Log.i("onDataChange", "Received: ${entriesList.values.joinToString { list -> list.joinToString() }}")
                setCalendarDate(dateHashMap, cal, calendar)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

        val dateTimeNowMidnight = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)

        databaseRef.child("entries").child(userId)
            .orderByChild("created")
            .startAt(dateTimeNowMidnight.withDayOfMonth(1).atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()?.toDouble()!!)
            .endAt(dateTimeNowMidnight.withDayOfMonth(LocalDate.now().lengthOfMonth()).plusDays(1).minusSeconds(1).atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()?.toDouble()!!)
            .addValueEventListener(valueEventListener)

        for (i in 0..LocalDate.now().lengthOfMonth()) {
            var mood = "default"

            val curDayEntriesList = entriesList[i]

            var moodAvg: Int = -1

            if (curDayEntriesList != null) {
                moodAvg = curDayEntriesList[0].mood

                for (j in 1..curDayEntriesList.size) {
                    val curMood = curDayEntriesList[j].mood

                    if (curMood != -1) {
                        moodAvg += curMood
                    }
                }

                moodAvg /= curDayEntriesList.size
            }

            when (moodAvg) {
                -1 -> mood = "default"
                0 -> mood = "veryBad"
                1 -> mood = "bad"
                2 -> mood = "neutral"
                3 -> mood = "good"
                4 -> mood = "veryGood"
            }

            dateHashMap[i] = mood
        }

        dateHashMap[cal.get(Calendar.DAY_OF_MONTH)] = "current"

        calendar.setDate(cal, dateHashMap)
    }

    private fun setCalendarDate(dateHashMap: HashMap<Int, Any>, cal: Calendar, calendar: CustomCalendar) {
        for (i in 0..LocalDate.now().lengthOfMonth()) {
            var mood = "default"

            val curDayEntriesList = entriesList[i]

            var moodAvg: Int = -1

            if (curDayEntriesList != null) {
                moodAvg = curDayEntriesList[0].mood

                for (j in 1 until curDayEntriesList.size) {
                    val curMood = curDayEntriesList[j].mood

                    if (curMood != -1) {
                        moodAvg += curMood
                    }
                }

                moodAvg /= curDayEntriesList.size
            }

            when (moodAvg) {
                -1 -> mood = "default"
                0 -> mood = "veryBad"
                1 -> mood = "bad"
                2 -> mood = "neutral"
                3 -> mood = "good"
                4 -> mood = "veryGood"
            }

            dateHashMap[i] = mood
        }

        dateHashMap[cal.get(Calendar.DAY_OF_MONTH)] = "current"

        calendar.setDate(cal, dateHashMap)
    }
}
