package com.mainthrowsexception.moodtrackingapp.ui.common.contract

import org.naishadhparmar.zcustomcalendar.CustomCalendar

interface CalendarContract {
    interface Presenter {
        fun initCalendar(calendar: CustomCalendar)
    }

    interface View
}
