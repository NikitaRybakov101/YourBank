package com.example.yourbank.dataBaseRoom.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historySend")
class HistorySendEntities(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,

    @ColumnInfo(name = "bin")       val bin: String,
    @ColumnInfo(name = "nameUser")  val nameUser: String,
    @ColumnInfo(name = "time")      val time: String
)