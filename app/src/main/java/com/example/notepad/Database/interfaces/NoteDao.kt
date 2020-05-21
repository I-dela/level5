package com.example.notepad.Database.interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notepad.Models.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note:Note)

    /**
     * return a Note wrapped inside a LiveData object.
     */
    @Query("SELECT * FROM Note LIMIT 1")
    fun getNotePad(): LiveData<Note?>

    @Update
    suspend fun updateNote(note:Note)



}