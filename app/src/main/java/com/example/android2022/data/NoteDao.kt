package com.example.android2022.data

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface NoteDao {

    @Insert(onConflict = REPLACE)
    suspend fun save(note:Note)

    @Delete
    suspend fun delete(note:Note)

    @Query("DELETE FROM notes")
    suspend fun deleteAll()

    //Если не находит возвращает NULL
    @Query("SELECT * FROM notes WHERE id=:id")
    suspend fun get(id: Int) : Note

    //Если не находит возвращает пустой список
    @Query("SELECT * FROM notes")
    suspend fun getAll() : List<Note>

    @Update()
    suspend fun update(note:Note)

}