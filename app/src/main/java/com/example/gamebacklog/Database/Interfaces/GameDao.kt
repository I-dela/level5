package com.example.gamebacklog.Database.Interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gamebacklog.Models.Game

@Dao
interface GameDao {


    @Insert
     suspend fun insertGame(game: Game)

    @Query("SELECT * FROM GAME ORDER BY releaseDate ASC")
    fun getGames() : LiveData<List<Game>>

    @Update
    suspend fun updateGame(game: Game)

    @Delete
    suspend fun  deleteGame(game: Game)

    @Query("DELETE FROM Game")
    suspend fun deleteAllGames()
}