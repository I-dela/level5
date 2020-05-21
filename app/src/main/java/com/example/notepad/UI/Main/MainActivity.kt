package com.example.notepad.UI.Main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.notepad.Database.NotepadRoomDatabase
import com.example.notepad.R
import com.example.notepad.UI.Edit.EditActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
          startEditActivity()
        }

        initViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initViewModel() {
        /*
        ViewModels are initialized using ViewModelProviders. Using this will let the Architecture
        Components initialize the ViewModel for us which also makes it able to be lifecycle aware.
         */
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.note.observe(this, Observer{ note ->
            if(note!= null){
                noteTitle.text = note.title
                tvNote.text = note.text
                updateStatus.text = getString(R.string.updateStatus, note.lastUpdated.toString())


            }


        } )

    }

    private fun startEditActivity(){
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra(EditActivity.EXTRA_NOTE, mainActivityViewModel.note.value)
        startActivity(intent)

    }
}
