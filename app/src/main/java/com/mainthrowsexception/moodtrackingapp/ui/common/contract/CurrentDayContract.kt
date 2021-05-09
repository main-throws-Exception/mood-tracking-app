package com.mainthrowsexception.moodtrackingapp.ui.common.contract

import com.mainthrowsexception.moodtrackingapp.database.model.Entry

interface CurrentDayContract {
    interface Presenter {
        fun getEntries()
//        fun onDateChanged()
    }

    interface View {
        fun onCurrentDayReady()
        fun onEntriesRead(entries: ArrayList<Entry>)
    }
}
