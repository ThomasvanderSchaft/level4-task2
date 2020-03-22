package com.example.rockpaperscissors.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*
import com.example.rockpaperscissors.model.Result

@Parcelize
@Entity
data class Result(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,
    @ColumnInfo(name = "user")
    var user: Int,
    @ColumnInfo(name = "computer")
    var computer: Int,
    @ColumnInfo(name = "winner")
    var winner: String,
    @ColumnInfo(name = "date")
    var date: Date
) : Parcelable