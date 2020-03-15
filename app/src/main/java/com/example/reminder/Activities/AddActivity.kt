package com.example.reminder.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.reminder.R
import com.example.reminder.Models.Reminder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*


const val EXTRA_REMINDER = "EXTRA_REMINDER"

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViews()
    }


    private fun initViews() {

        fab.setOnClickListener { addReminder() }
    }

    private fun addReminder(){
        if(etAddReminder.text.toString().isBlank()){
            return Snackbar.make(etAddReminder,"reminder cannot be empty!", Snackbar.LENGTH_LONG).show()

        }

        val reminder =
            Reminder(etAddReminder.text.toString())

        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_REMINDER, reminder)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()

        etAddReminder.text?.clear()


    }


    override fun onBackPressed() {
        startActivity(Intent(this , MainActivity::class.java))
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }





}
