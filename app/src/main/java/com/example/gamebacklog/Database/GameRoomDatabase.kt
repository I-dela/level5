package com.example.gamebacklog.Database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gamebacklog.Database.Helpers.Converters
import com.example.gamebacklog.Database.Interfaces.GameDao
import com.example.gamebacklog.Models.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [ Game::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract  class GameRoomDatabase  : RoomDatabase(){

    abstract fun gameDao(): GameDao


    companion object{

        private const val DATABASE_NAME = "GAME_DATABASE"


        @Volatile
        private var INSTANCE :GameRoomDatabase? = null

        fun getDatabase(context:Context) : GameRoomDatabase?{
            if (INSTANCE == null){
                synchronized(GameRoomDatabase::class.java){
                    if(INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            GameRoomDatabase::class.java,
                            DATABASE_NAME
                        ).fallbackToDestructiveMigration()
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate( db: SupportSQLiteDatabase) {
                                    Log.d("VADERRR", "MOeder")

                                    super.onCreate(db)


                                    INSTANCE?.let { database ->
                                        CoroutineScope(Dispatchers.IO).launch {


                                            val cal2 = Calendar.getInstance()
                                            cal2[Calendar.YEAR] = 2012
                                            cal2[Calendar.MONTH] = Calendar.JANUARY
                                            cal2[Calendar.DAY_OF_MONTH] = 1
                                            val dateRepresentation2 = cal2.time
                                            database.gameDao().insertGame(Game("Assassins creed 3 ", "ps4", dateRepresentation2))

                                            val cal = Calendar.getInstance()
                                            cal[Calendar.YEAR] = 2010
                                            cal[Calendar.MONTH] = Calendar.JANUARY
                                            cal[Calendar.DAY_OF_MONTH] = 1
                                            val dateRepresentation = cal.time
                                            database.gameDao().insertGame(Game("Assassins creed 2 ", "ps4",
                                                dateRepresentation))


                                        }
                                    }

                                }

                            })
                            .build()
                    }
                }
            }

            return INSTANCE
        }

    }
}