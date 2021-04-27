package com.mainthrowsexception.moodtrackingapp.database.service

import com.mainthrowsexception.moodtrackingapp.database.model.Entry

sealed class ServiceResult {
    data class Success(val entry: List<Entry>) : ServiceResult()
    object Failure : ServiceResult()
    object NotFound : ServiceResult()
}
