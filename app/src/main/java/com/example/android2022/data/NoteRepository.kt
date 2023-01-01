package com.example.android2022.data

import android.content.Context
import androidx.room.Room

class NoteRepository(context: Context) {

    private val db by lazy {
        Room.databaseBuilder(context,AppDatabase::class.java, DATABASE_NAME)
            .build()
    }

    private val noteDao by lazy {
        db.getNoteDao()
    }

//    //1 вариант (ручками)
//    suspend fun getAllNotes() : List<Note> = withContext(Dispatchers.IO) {
//        noteDao.getAll()
//    }

    //2 вариант (готовенькое)
    suspend fun getAll() : List<Note> = noteDao.getAll()

    suspend fun get(id:Int) : Note = noteDao.get(id)

    suspend fun delete(note: Note) = noteDao.delete(note)

    suspend fun deleteAll() = noteDao.deleteAll()

    suspend fun save(note: Note) = noteDao.save(note)

    suspend fun update(note: Note) = noteDao.update(note)

    companion object {
        private const val DATABASE_NAME = "NoteDb"
    }
}