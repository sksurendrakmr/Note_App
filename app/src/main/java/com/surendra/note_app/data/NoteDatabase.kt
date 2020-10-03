package com.surendra.note_app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao():NoteDao

    companion object {
        //volatile means this instance is immediately available for all the other thread
        @Volatile private var instance:NoteDatabase ?=null
        private val LOCK=Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance?:buildDatabase(context).also {
                instance=it //assigning the return database to instance
            }
        }
        //This function will return the database
        private fun buildDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,NoteDatabase::class.java,"note_database").build()
    }
}