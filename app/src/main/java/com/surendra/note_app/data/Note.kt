package com.surendra.note_app.data

import androidx.room.Entity


@Entity
data class Note(
    val id:Int,
    val title:String,
    val note:String
)