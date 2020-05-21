package com.example.notepad.UI.Edit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.notepad.R
import com.example.notepad.UI.Main.MainActivity
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_edit.*
import java.util.*

class EditActivity : AppCompatActivity() {

    private lateinit var editViewModel: EditViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setTitle(R.string.editActivityTitle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
        initViewModel()


    }


    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }


    private fun initViews() {
        fab2.setOnClickListener {

            /*
            Note: In the code block presented the apply method is used from Kotlin. With this you can reference an object and
            within the curly brackets you can call any method of the object without having to reference the object multiple times.
             */
            editViewModel.note.value?.apply {
                title = etNoteTitle.text.toString()
                lastUpdated = Date()
                text = etNote.text.toString()
            }

            editViewModel.updateNote()
        }
    }

    private fun initViewModel() {
        /*
        When the business logic is moved the the ViewModel the question arises of how the activity will know when a
         task has been completed or if errors occurred. One way to solve this is by adding MutableLiveData objects
         to the ViewModel which hold the state (error, success). These objects are observed in the EditActivity and
          whenever an error value is changed the observer gets notified about it. If the error message is not null
           then we want to display it using a Toast message. The same is applies for the success state. Whenever
            the note has been updated successfully then we want to finish the activity. This could also be done
             for a loading state (displaying a loading circle when the note is updated).

         */
        editViewModel = ViewModelProvider(this).get(EditViewModel::class.java)
        editViewModel.note.value = intent.extras?.getParcelable(EXTRA_NOTE)!!

        editViewModel.note.observe(this, Observer { note ->
            if (note != null) {
                etNoteTitle.setText(note.title)
                etNote.setText(note.text)
            }
        })

        editViewModel.error.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        editViewModel.succes.observe(this, Observer { success ->
            if (success) finish()
        })
    }


    /*
    At the start the ViewModel doesn’t have a note yet. Because this is sent in the extras of the intent.
    We need to manually set this object to the Note that was sent by the MainActivity.
     Then we observe the object and populate the EditText widgets with the title and text of the note.
     */
    companion object {
        const val EXTRA_NOTE = "EXTRA_NOTE"
    }

}

