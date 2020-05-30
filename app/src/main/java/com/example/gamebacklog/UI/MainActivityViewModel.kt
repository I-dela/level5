package com.example.gamebacklog.UI

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.gamebacklog.Database.Repositories.GameRepository
import com.example.gamebacklog.Models.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application : Application) : AndroidViewModel(application) {
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val gameRepository = GameRepository(application.applicationContext)

    val games: LiveData<List<Game>> = gameRepository.getGames()

    fun insertGame(game: Game) {
        ioScope.launch {
            gameRepository.insertGame(game)
        }
    }


    fun deleteGame(game: Game){
        ioScope.launch {
            gameRepository.deleteGame(game)
        }
    }

    fun deleteAllGames(){
        ioScope.launch {
            gameRepository.deleteAllGames()
        }
    }




}