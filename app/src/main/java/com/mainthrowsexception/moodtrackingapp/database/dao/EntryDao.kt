package com.mainthrowsexception.moodtrackingapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mainthrowsexception.moodtrackingapp.database.model.Entry

@Dao
interface EntryDao {
    @Insert
    fun insert(entry: Entry): Long
    @Update
    fun update(entry: Entry): Int
    @Query("DELETE FROM entries WHERE user_id = :userId")
    fun deleteByUserId(userId: Long)
    @Query("SELECT * FROM entries where user_id = :userId ORDER BY created")
    fun findByUserId(userId: Long): LiveData<List<Entry>>
    @Query("SELECT * FROM entries where id = :id")
    fun findById(id: Long): Entry?
    @Query("SELECT * FROM entries where user_id = :userId AND created BETWEEN :createdLowerBound AND :createdUpperBound ORDER BY created")
    fun findByUserIdAndCreatedBetween(userId: Long, createdLowerBound: Long, createdUpperBound: Long): LiveData<List<Entry>>
}