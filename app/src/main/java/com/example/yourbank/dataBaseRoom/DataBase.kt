package com.example.yourbank.dataBaseRoom

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yourbank.dataBaseRoom.entities.YourSavedCardEntities
import com.example.yourbank.dataBaseRoom.dao.DaoDbYourCard
import com.example.yourbank.dataBaseRoom.entities.HistorySendEntities

@Database(entities = [YourSavedCardEntities::class, HistorySendEntities::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun dataBase() : DaoDbYourCard
}