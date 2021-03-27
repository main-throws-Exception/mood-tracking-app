package com.mainthrowsexception.moodtrackingapp

import android.text.style.TtsSpan
import java.util.*

data class Entry(val mood: Int,
                 val tags: List<String>,
                 val time: Date) {
}