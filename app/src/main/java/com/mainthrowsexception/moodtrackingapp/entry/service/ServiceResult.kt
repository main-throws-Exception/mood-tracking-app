package com.mainthrowsexception.moodtrackingapp.entry.service

import com.mainthrowsexception.moodtrackingapp.entry.model.Entry

sealed class ServiceResult {
    data class Success(val entry: Entry) : ServiceResult()
    object Failure : ServiceResult()
    object NotFound : ServiceResult()
}
