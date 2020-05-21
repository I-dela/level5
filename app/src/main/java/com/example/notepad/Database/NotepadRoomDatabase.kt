
package com.example.notepad.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notepad.Database.Helpers.Converters
import com.example.notepad.Database.interfaces.NoteDao
import com.example.notepad.Models.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull
import java.util.*
import android.util.Log
import androidx.annotation.CallSuper


@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NotepadRoomDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao



    companion object {
        private const val DATABASE_NAME = "NOTEPAD_DATABASE"

        @Volatile
        private var INSTANCE: NotepadRoomDatabase? = null

        fun getDatabase(context: Context): NotepadRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(NotepadRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                                context.applicationContext,
                                NotepadRoomDatabase::class.java, DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate( db: SupportSQLiteDatabase) {
                                    Log.d("VADERRR", "JeMOeder")

                                    super.onCreate(db)
                                    INSTANCE?.let { database ->
                                        CoroutineScope(Dispatchers.IO).launch {
                                            database.noteDao().insertNote(Note("Notepad", Date(), "vdpospvmsdpvsp"))
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
