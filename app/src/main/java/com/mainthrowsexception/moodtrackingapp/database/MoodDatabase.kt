package com.mainthrowsexception.moodtrackingapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mainthrowsexception.moodtrackingapp.database.dao.EntryDao
import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import com.mainthrowsexception.moodtrackingapp.database.model.Tag
import com.mainthrowsexception.moodtrackingapp.database.model.User

@Database(entities = [Entry::class, Tag::class, User::class], version = 1)
abstract class MoodDatabase : RoomDatabase() {
    abstract val entryDao: EntryDao

    companion object {
        @Volatile
        private var INSTANCE: MoodDatabase? = null

        fun getInstance(context: Context): MoodDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MoodDatabase::class.java,
                        "mood_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}