package com.omen.chitrakavya.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.omen.chitrakavya.models.PoemListItem

@Dao
interface PoemDao {
    @Insert
    suspend fun addPoems(poems: List<PoemListItem>)

    @Query("SELECT * FROM poem")
    suspend fun getPoems() : List<PoemListItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllPoems(poems: List<PoemListItem>)
}