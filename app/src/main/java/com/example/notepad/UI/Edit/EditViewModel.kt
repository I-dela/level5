package com.example.notepad.UI.Edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.notepad.Database.Repositories.NoteRepository
import com.example.notepad.Models.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * This class demonstrates how the business logic is moved to the ViewModel
 * and the activity is notified with errors using the LiveData objects.
 */

class EditViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository = NoteRepository(application.applicationContext)

    //Note: opposed the previous step here we need to use the Main scope because a value from LiveData can’t be set in a background thread
    private val mainScope = CoroutineScope(Dispatchers.Main)


    /*
    n this class we use the type MutableLiveData which is a subtype of LiveData. Just as with val and var,
     LiveData is immutable and MutableLiveData is mutable. We have defined an error LiveData which
     the activity can observe and display an error message when an error occurred.
     The same is applied to the success boolean.
     */
    val note = MutableLiveData<Note?>()

    val error = MutableLiveData<String?>()

    val succes = MutableLiveData<Boolean>()


    /**
     * The updateNote method uses the isNoteValid method to check if the note is valid.
     * If the return type was true then it uses the noterepository
     * to udpate the note currently present in the MutableLiveData note object.
     * If the not is updated then the value of success is set to true.
     */

    fun updateNote() {
        if (isNoteValid()) {
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    noteRepository.updateNotePad(note.value!!)
                }
                succes.value = true
            }
        }
    }


    /**
     * The method isNoteValid validates if the note from the MutableLiveData is valid according to
     * our business rules (note must not be null and the title must not be empty).
     * If either of those business rules is violated then the value of the error object is changed to the error.
     */
    private fun isNoteValid(): Boolean {
        return when {
            note.value == null -> {
                error.value = "Note must not be null"
                false
            }
            note.value!!.title.isBlank() -> {
                error.value = "Title must not be empty"
                false
            }
            else -> true
        }
    }


}