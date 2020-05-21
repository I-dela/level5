package com.example.notepad.Database.Repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.notepad.Database.NotepadRoomDatabase
import com.example.notepad.Database.interfaces.NoteDao
import com.example.notepad.Models.Note

class NoteRepository(context: Context) {

    private val noteDao : NoteDao

    init {
        val database = NotepadRoomDatabase.getDatabase(context)
        noteDao = database!!.noteDao()
    }

    fun getNotePad() : LiveData<Note?>{
        return noteDao.getNotePad()
    }

    suspend fun updateNotePad(note:Note){
        return noteDao.updateNote(note)
    }
}