package com.mainthrowsexception.moodtrackingapp.ui.common.contract

import org.naishadhparmar.zcustomcalendar.CustomCalendar
import java.util.*

interface CalendarContract {
    interface Presenter {
        fun initCalendar(calendar: CustomCalendar)
        fun onMonthChanged(newMonth: Calendar?)
    }

    interface View {
        fun onCalendarReady()
    }
}
