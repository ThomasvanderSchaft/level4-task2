package com.example.rockpaperscissors.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rockpaperscissors.Converter
import com.example.rockpaperscissors.model.Result

@Database(entities = [Result::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ResultRoomDatabase : RoomDatabase() {
    abstract fun resultDAO(): ResultDao

    companion object {
        private const val DATABASE_NAME = "RESULTS_DATABASE"

        @Volatile
        private var resultRoomDatabaseInstance: ResultRoomDatabase? = null

        fun getDatabase(context: Context): ResultRoomDatabase? {
            if (resultRoomDatabaseInstance == null) {
                synchronized(ResultRoomDatabase::class.java) {
                    if (resultRoomDatabaseInstance == null) {
                        resultRoomDatabaseInstance =
                            Room.databaseBuilder(context.applicationContext,
                                ResultRoomDatabase::class.java,
                                DATABASE_NAME
                            ).build()
                    }
                }
            }
            return resultRoomDatabaseInstance
        }
    }
}