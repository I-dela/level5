package com.example.gamebacklog.Database.Repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.gamebacklog.Database.GameRoomDatabase
import com.example.gamebacklog.Database.Interfaces.GameDao
import com.example.gamebacklog.Models.Game

class GameRepository(context: Context) {
    private val gameDao : GameDao


    init {
        val database = GameRoomDatabase.getDatabase(context)

        gameDao = database!!.gameDao()
    }

    fun getGames () :  LiveData<List<Game>>{
        return gameDao.getGames()
    }

    suspend fun insertGame(game: Game){
         gameDao.insertGame(game)
    }


    suspend fun  deleteGame(game: Game){
        gameDao.deleteGame(game)
    }

    suspend fun  deleteAllGames(){
        gameDao.deleteAllGames()
    }

}