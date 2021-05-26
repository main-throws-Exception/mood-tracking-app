package com.mainthrowsexception.moodtrackingapp.database.model.dto

import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import javax.sql.StatementEvent

data class EntryDto(
    var uid: String = "",
    var userId: String = "",
    var mood: Int = -1,
    var note: String = "",
    var created: Long = System.currentTimeMillis()
) {
    constructor (copy: EntryDto): this(
            uid = copy.uid,
            userId = copy.userId,
            mood = copy.mood,
            note = copy.note,
            created = copy.created
        )

    constructor (copy: Entry): this(
        uid = copy.uid,
        userId = copy.userId,
        mood = copy.mood,
        note = copy.note,
        created = copy.created
    )
}
