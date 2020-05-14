package com.example.reminder.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reminder.R
import com.example.reminder.Models.Reminder
import com.example.reminder.Adapters.ReminderAdapter
import com.example.reminder.ViewModel.MainActivityViewModel
import androidx.activity.viewModels
import androidx.lifecycle.Observer


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

const val ADD_REMINDER_REQUEST_CODE = 100
const val TAG = "MainActivity"

/*
Inserting and deleting a reminder has been changed to use the newly made methods from the ViewModel.
 Because the viewmodel now takes care of all repository operations the ReminderRepository class variable can be removed.
 All repository interactions have now been moved to the viewmodel layer.
 */

class MainActivity : AppCompatActivity() {

    private val reminders = arrayListOf<Reminder>()
    private val reminderAdapter = ReminderAdapter(reminders)


    //ViewModels are initialized using the viewModels() helper method from the activity-ktx artifact.
    // Using this will let the Architecture Components initialize the ViewModel for us which also
    // makes it able to be lifecycle aware.
    private val viewModel: MainActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        initViews()

        fab.setOnClickListener {
            startAddActivity()
        }
        observeViewModel()

    }

    /**
     * contains the observer to the LiveData reminders object from the ViewModel
     */
    private fun observeViewModel() {
        //data changes then this Observer will get invoked.
        // Whenever the reminders list changes from the LiveData we want to update the recyclerview.
        viewModel.reminders.observe(this, Observer { reminders ->
            this@MainActivity.reminders.clear()
            this@MainActivity.reminders.addAll(reminders)
            reminderAdapter.notifyDataSetChanged()
        })

    }


    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter
        rvReminders.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvReminders.adapter = reminderAdapter
        rvReminders.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )


        createItemTouchHelper().attachToRecyclerView(rvReminders)

    }


    /**
     * Create a touch helper to recognize when a user swipes an item from a recycler view.
     * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
     * and uses callbacks to signal when a user is performing these actions.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            // onSwiped has been changed to use the ViewModel deleteReminder method
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val reminderToDelete = reminders[position]


                viewModel.deleteReminder(reminderToDelete)


                Snackbar.make(rvReminders, "Item removed", Snackbar.LENGTH_LONG).show()

            }
        }
        return ItemTouchHelper(callback)
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


    private fun startAddActivity() {
        val intent = Intent(this, AddActivity::class.java)
        startActivityForResult(
            intent,
            ADD_REMINDER_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_REMINDER_REQUEST_CODE -> {
                    data?.let {
                        val reminder = data!!.getParcelableExtra<Reminder>(
                            EXTRA_REMINDER
                        )

                        reminder?.let {safeReminder ->

                                viewModel.insertReminder(safeReminder)



                        }?: run {
                            Log.e(TAG, "reminder is null")
                        }
                    } ?: run {
                        Log.e(TAG, "null intent data received")
                    }



                }
                }
            }

        }

    }

