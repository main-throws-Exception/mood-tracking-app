package com.mainthrowsexception.moodtrackingapp.ui.common.contract

import android.view.View
import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import com.mainthrowsexception.moodtrackingapp.database.model.Tag
import com.mainthrowsexception.moodtrackingapp.ui.entry.EntryFragment
import java.security.KeyStore

interface EntryContract {
    interface Presenter {
        fun bindView()
        fun saveChanges(saveEntry: Entry)
    }

    interface View {
        fun bindData(entry: Entry)
        fun saveData()
    }
}