package com.mainthrowsexception.moodtrackingapp.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class Entry (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "user_id")
    val userId: Long = 0L,
    val note: String = "",
    val mood: Int = -1,
    val created: Long = System.currentTimeMillis(),
    val updated: Long = System.currentTimeMillis()
)
