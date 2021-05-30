package com.mainthrowsexception.moodtrackingapp.ui.common.presenter

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
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
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CalendarPresenter(view: CalendarContract.View) : CalendarContract.Presenter {

    private var view: CalendarContract.View? = view
    private val entriesList: TreeMap<Int, MutableList<Entry>> = TreeMap<Int, MutableList<Entry>>()
    private val userId = FirebaseAuth.getInstance().currentUser!!.uid
    private val databaseRef =
        Firebase.database("https://goodmood-c69a1-default-rtdb.europe-west1.firebasedatabase.app/").reference
    private lateinit var currentValueEventListener: ValueEventListener
    private lateinit var currentQuery: Query
    private lateinit var customCalendar: CustomCalendar

    override fun initCalendar(calendar: CustomCalendar) {
        customCalendar = calendar

        // Default
        val descHashMap: HashMap<Any, Property> = HashMap()

        val disabledProperty = Property()

        disabledProperty.layoutResource = R.layout.calendar_disabled_view
        disabledProperty.dateTextViewResource = R.id.calendar_disabled_view__text

        descHashMap["disabled"] = disabledProperty

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

        customCalendar.setMapDescToProp(descHashMap)

        val cal: Calendar = Calendar.getInstance()

        currentValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                entriesList.clear()

                for (entrySnapshot in snapshot.children) {
                    val entry = entrySnapshot.getValue(Entry::class.java)
                    val dayOfMonth = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(entry!!.created),
                        ZoneOffset.UTC
                    ).dayOfMonth

                    if (entriesList[dayOfMonth] == null) {
                        entriesList[dayOfMonth] = ArrayList(listOf(entry))
                    } else {
                        entriesList[dayOfMonth]?.add(entry)
                    }
                }

                Log.i(
                    "onDataChange",
                    "Received: ${entriesList.values.joinToString { list -> list.joinToString() }}"
                )

                setCalendarDate(cal, customCalendar, getCalendarDate(cal))

                view?.onCalendarReady()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

//        val dateTimeNowMidnight = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
//
//        currentQuery = databaseRef.child("entries").child(userId)
//            .orderByChild("created")
//            .startAt(
//                dateTimeNowMidnight.withDayOfMonth(1).atZone(ZoneOffset.UTC)?.toInstant()
//                    ?.toEpochMilli()?.toDouble()!!
//            )
//            .endAt(
//                dateTimeNowMidnight.plusMonths(1).minusSeconds(1).atZone(ZoneOffset.UTC)
//                    ?.toInstant()?.toEpochMilli()?.toDouble()!!
//            )

        val calendarAtStartOfMonth = cal.clone() as Calendar
        calendarAtStartOfMonth.set(Calendar.DAY_OF_MONTH, 1)
        calendarAtStartOfMonth.set(Calendar.HOUR, 0)
        calendarAtStartOfMonth.clear(Calendar.MINUTE)
        calendarAtStartOfMonth.clear(Calendar.SECOND)
        calendarAtStartOfMonth.clear(Calendar.MILLISECOND)

        val calendarAtEndOfMonth = calendarAtStartOfMonth.clone() as Calendar
        calendarAtEndOfMonth.add(Calendar.MONTH, 1)

        currentQuery = databaseRef.child("entries").child(userId)
            .orderByChild("created")
            .startAt(
                calendarAtStartOfMonth.timeInMillis.toDouble()
            )
            .endAt(
                calendarAtEndOfMonth.timeInMillis.toDouble()
            )

        currentQuery.addValueEventListener(currentValueEventListener)
    }

    override fun onMonthChanged(newMonth: Calendar?) {
        val calendarAtStartOfMonth = newMonth?.clone() as Calendar
        calendarAtStartOfMonth.set(Calendar.DAY_OF_MONTH, 1)
        calendarAtStartOfMonth.set(Calendar.HOUR, 0)
        calendarAtStartOfMonth.clear(Calendar.MINUTE)
        calendarAtStartOfMonth.clear(Calendar.SECOND)
        calendarAtStartOfMonth.clear(Calendar.MILLISECOND)

        val calendarAtEndOfMonth = calendarAtStartOfMonth.clone() as Calendar
        calendarAtEndOfMonth.add(Calendar.MONTH, 1)

        currentQuery.removeEventListener(currentValueEventListener)

        currentQuery = databaseRef.child("entries").child(userId)
            .orderByChild("created")
            .startAt(
                calendarAtStartOfMonth.timeInMillis.toDouble()
            )
            .endAt(
                calendarAtEndOfMonth.timeInMillis.toDouble()
            )

        currentValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                entriesList.clear()

                for (entrySnapshot in snapshot.children) {
                    val entry = entrySnapshot.getValue(Entry::class.java)
                    val dayOfMonth = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(entry!!.created),
                        ZoneOffset.UTC
                    ).dayOfMonth

                    if (entriesList[dayOfMonth] == null) {
                        entriesList[dayOfMonth] = ArrayList(listOf(entry))
                    } else {
                        entriesList[dayOfMonth]?.add(entry)
                    }
                }

                Log.i(
                    "onDataChange",
                    "Received: ${entriesList.values.joinToString { list -> list.joinToString() }}"
                )

                setCalendarDate(newMonth, customCalendar, getCalendarDate(newMonth))

                view?.onCalendarReady()
            }

            override fun onCancelled(error: DatabaseError) {
                return
            }
        }

        currentQuery.addValueEventListener(currentValueEventListener)
    }

    private fun setCalendarDate(
        cal: Calendar,
        calendar: CustomCalendar,
        dateHashMap: MutableMap<Int, Any>
    ) {
        calendar.setDate(cal, dateHashMap)
        view?.onCalendarReady()
    }

    private fun getCalendarDate(
        cal: Calendar
    ) : MutableMap<Int, Any> {
        val dateHashMap: MutableMap<Int, Any> = HashMap()

        for (i in 0..cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
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

        if ((cal.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)) &&
            (cal.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH))) {
                Log.i("currentDateCheck", "passed")
            dateHashMap[cal.get(Calendar.DAY_OF_MONTH)] = "current"
        }

        return dateHashMap
    }
}
