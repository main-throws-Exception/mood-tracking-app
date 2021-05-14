package com.mainthrowsexception.moodtrackingapp.ui.common.contract

import com.mainthrowsexception.moodtrackingapp.database.model.Entry

interface ChartsContract {
    interface Presenter {
        fun getData()
        fun prepareMoods(entriesList: ArrayList<Entry>)
        fun prepareTags(entriesList: ArrayList<Entry>)
    }

    interface View {
        fun onMoodsReady(moods: ArrayList<Float>)
        fun onTagsReady(tagsMap: HashMap<String, Int>)
        fun onChartsReady()
    }
}
