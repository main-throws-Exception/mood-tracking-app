package com.mainthrowsexception.moodtrackingapp.database.api

interface DbListener {
    fun onDataReady()
    fun onDataFailed()
}