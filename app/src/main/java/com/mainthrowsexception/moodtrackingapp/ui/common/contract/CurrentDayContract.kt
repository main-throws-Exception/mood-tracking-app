package com.mainthrowsexception.moodtrackingapp.ui.common.contract

import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import java.time.ZonedDateTime

interface CurrentDayContract {
    interface Presenter {
        fun getEntries(zonedDateTime: ZonedDateTime)
    }

    interface View {
        fun onCurrentDayReady()
        fun onEntriesRead(entries: ArrayList<Entry>)
        fun onDayChanged(back: Boolean)
    }
}
