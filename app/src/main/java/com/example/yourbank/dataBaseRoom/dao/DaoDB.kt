package com.example.yourbank.dataBaseRoom.dao

import androidx.room.Dao

@Dao
interface DaoDB {
 /*   @Query("SELECT * FROM notePaint")
    fun getAllNotesPaint() : List<EntitiesNotesPaint>

    @Query("SELECT * FROM noteStandard")
    fun getAllNotesStandard() : List<Any>

    @Query("SELECT * FROM noteFood")
    fun getAllNotesFood() : List<Any>


    @Query("DELETE FROM notePaint WHERE idCard LIKE :id")
    fun deleteNotePaint(id : Int) : Int

    @Query("DELETE FROM noteStandard WHERE idCard LIKE :id")
    fun deleteNoteStandard(id : Int) : Int

    @Query("DELETE FROM noteFood WHERE idCard LIKE :id")
    fun deleteNoteFood(id : Int) : Int

    @Query("DELETE FROM notePaint")
    fun deleteAll() : Int


    @Query("UPDATE notePaint SET cardPositionX = :x WHERE idCard IN (:id)")
    fun updateCoordinatesCardNotesPaintX(x : Int, id: Int)

    @Query("UPDATE notePaint SET cardPositionY = :y WHERE idCard IN (:id)")
    fun updateCoordinatesCardNotesPaintY(y : Int, id: Int)

    @Query("UPDATE notePaint SET elevation = :elevation WHERE idCard IN (:id)")
    fun updateCardElevationPaint(elevation: Int, id: Int)


    @Query("UPDATE noteStandard SET cardPositionX = :x WHERE idCard IN (:id)")
    fun updateCoordinatesCardNotesStandardX(x : Int, id: Int)

    @Query("UPDATE noteStandard SET cardPositionY = :y WHERE idCard IN (:id)")
    fun updateCoordinatesCardNotesStandardY(y : Int, id: Int)

    @Query("UPDATE noteStandard SET elevation = :elevation WHERE idCard IN (:id)")
    fun updateCardElevationStandard(elevation: Int, id: Int)


    @Query("UPDATE noteFood SET cardPositionX = :x WHERE idCard IN (:id)")
    fun updateCoordinatesCardNotesFoodX(x : Int, id: Int)

    @Query("UPDATE noteFood SET cardPositionY = :y WHERE idCard IN (:id)")
    fun updateCoordinatesCardNotesFoodY(y : Int, id: Int)

    @Query("UPDATE noteFood SET elevation = :elevation WHERE idCard IN (:id)")
    fun updateCardElevationFood(elevation: Int, id: Int)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotePaint(note : EntitiesNotesPaint)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNoteStandard(note : Any)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNoteFood(note : Any)*/
}