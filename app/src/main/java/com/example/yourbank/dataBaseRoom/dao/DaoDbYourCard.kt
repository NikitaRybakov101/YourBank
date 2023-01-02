package com.example.yourbank.dataBaseRoom.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yourbank.dataBaseRoom.entities.YourSavedCardEntities

@Dao
interface DaoDbYourCard {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(note : YourSavedCardEntities)

    @Query("SELECT * FROM savedCard")
    fun getAllSavedCard() : List<YourSavedCardEntities>

    @Query("DELETE FROM savedCard WHERE bin LIKE :bin")
    fun deleteSavedCard(bin: String) : Int
}