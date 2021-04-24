package com.mainthrowsexception.moodtrackingapp.screen.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mainthrowsexception.moodtrackingapp.R
import org.naishadhparmar.zcustomcalendar.CustomCalendar
import org.naishadhparmar.zcustomcalendar.Property
import java.util.*
import kotlin.collections.HashMap

class CalendarFragment : Fragment() {

    lateinit var calendar: CustomCalendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_calendar, container, false)

        calendar = view.findViewById(R.id.fragment_calendar__calendar)

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

        for (i in 0..cal.getActualMaximum(Calendar.DATE)) {
            var mood = ""
            when ((0..4).random()) {
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

        var curToast: Toast = Toast.makeText(activity, "", Toast.LENGTH_SHORT)

        calendar.setOnDateSelectedListener { _, selectedDate, _ ->
            val sDate: String = selectedDate.get(Calendar.DAY_OF_MONTH).toString() +
                    "/" + (selectedDate.get(Calendar.MONTH) + 1).toString() +
                    "/" + (selectedDate.get(Calendar.YEAR)).toString()
            curToast.cancel()
            curToast = Toast.makeText(activity, sDate, Toast.LENGTH_SHORT)
            curToast.show()
        }

        return view
    }
}
