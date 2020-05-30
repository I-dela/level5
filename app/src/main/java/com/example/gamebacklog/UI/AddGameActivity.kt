package com.example.gamebacklog.UI

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.gamebacklog.Models.Game
import com.example.gamebacklog.R

import kotlinx.android.synthetic.main.activity_add_game.*
import kotlinx.android.synthetic.main.content_add_game.*
import java.time.format.DateTimeFormatter
import java.util.*

const val EXTRA_GAME = "EXTRA_GAME"


class AddGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game)
        setSupportActionBar(toolbar)

        initViews()

    }


    private fun initViews() {

        fab.setOnClickListener { addGame() }
    }

    private fun addGame(){
        if(tiTitle.text.toString().isBlank()){
            return Snackbar.make(tiTitle,"Title cannot be empty!", Snackbar.LENGTH_LONG).show()

        }

        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = tiYear.text.toString().toInt()
        cal[Calendar.MONTH] = tiMonth.text.toString().toInt() -1
        cal[Calendar.DAY_OF_MONTH] = tiDay.text.toString().toInt()
        val dateRepresentation = cal.time



        val game =
            Game(tiTitle.text.toString(), tiPlatform.text.toString(), dateRepresentation)

        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_GAME, game)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()


    }


    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}
