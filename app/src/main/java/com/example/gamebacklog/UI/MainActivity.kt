package com.example.gamebacklog.UI

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamebacklog.Adapters.GameAdapter
import com.example.gamebacklog.Models.Game
import com.example.gamebacklog.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.nio.file.Files.delete
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.Objects.compare

const val ADD_GAME_REQUEST_CODE = 100
const val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {

    private val games = arrayListOf<Game>()
    private val adapter = GameAdapter(games)

    private val viewModel: MainActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViews()


        fab.setOnClickListener { view ->
            startAddGameActivity()
        }


        observeViewModel()


    }

    private fun initViews() {
        rvGames.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvGames.adapter = adapter
        rvGames.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        createItemTouchHelper().attachToRecyclerView(rvGames)


    }

    private fun observeViewModel() {
        //data changes then this Observer will get invoked.
        // Whenever the reminders list changes from the LiveData we want to update the recyclerview.
        viewModel.games.observe(this, Observer { games ->
            this@MainActivity.games.clear()

            this@MainActivity.games.addAll(games)
            adapter.notifyDataSetChanged()
        })

    }



    private fun startAddGameActivity() {
        val intent = Intent(this, AddGameActivity::class.java)

        startActivityForResult(
            intent,
            ADD_GAME_REQUEST_CODE
        )

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

            R.id.delete -> {
                viewModel.deleteAllGames()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_GAME_REQUEST_CODE -> {
                    data?.let {
                        val game = data!!.getParcelableExtra<Game>(
                            EXTRA_GAME
                        )

                        game?.let { game ->

                            viewModel.insertGame(game)


                        } ?: run {
                            Log.e(TAG, "game is null")
                        }
                    } ?: run {
                        Log.e(TAG, "null intent data received")
                    }


                }
            }
        }

    }

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
                val gameToDelete = games[position]


                viewModel.deleteGame(gameToDelete)


                Snackbar.make(rvGames, "Game removed", Snackbar.LENGTH_LONG).show()

            }
        }
        return ItemTouchHelper(callback)
    }







}
