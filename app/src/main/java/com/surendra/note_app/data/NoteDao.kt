package com.surendra.note_app.data

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note:Note)

    @Query("SELECT * FROM note ORDER BY id DESC")
    suspend fun getAllNote():List<Note>

    //if we want to insert multiple values at once
//    @Insert
//    suspend fun insertMultipleNotes(vararg note:Note)
    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note:Note)
}