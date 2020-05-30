package com.example.gamebacklog.Models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "Game")
class Game(
    var title: String,

    var platform: String,

    var releaseDate: Date,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")

    var id: Long? = null
) : Parcelable {

}