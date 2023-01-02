package com.example.yourbank.dataBaseRoom.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yourbank.dataBaseRoom.entities.HistorySendEntities
import com.example.yourbank.dataBaseRoom.entities.YourSavedCardEntities

@Dao
interface DaoDbYourCard {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(card : YourSavedCardEntities)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistorySend(card : HistorySendEntities)

    @Query("SELECT * FROM savedCard")
    fun getAllSavedCard() : List<YourSavedCardEntities>

    @Query("SELECT * FROM historySend")
    fun getAllHistorySend() : List<HistorySendEntities>

    @Query("DELETE FROM savedCard WHERE bin LIKE :bin")
    fun deleteSavedCard(bin: String) : Int

    @Query("DELETE FROM historySend")
    fun deleteAllHistory() : Int
}