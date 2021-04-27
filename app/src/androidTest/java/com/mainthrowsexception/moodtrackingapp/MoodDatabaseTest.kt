package com.mainthrowsexception.moodtrackingapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mainthrowsexception.moodtrackingapp.database.MoodDatabase
import com.mainthrowsexception.moodtrackingapp.database.dao.EntryDao
import com.mainthrowsexception.moodtrackingapp.database.model.Entry
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MoodDatabaseTest {
    private lateinit var entryDao: EntryDao
    private lateinit var db: MoodDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, MoodDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        entryDao = db.entryDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetEntry() {
        val entryId = entryDao.insert(Entry())
        val foundEntry = entryDao.findById(entryId)
        assertEquals(-1, foundEntry?.mood)
    }
}