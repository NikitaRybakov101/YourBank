package com.example.yourbank.dataBaseRoom

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yourbank.dataBaseRoom.entities.EntitiesNotesPaint
import com.example.yourbank.dataBaseRoom.dao.DaoDB

@Database(entities = [EntitiesNotesPaint::class], version = 4, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun dataBase() : DaoDB
}