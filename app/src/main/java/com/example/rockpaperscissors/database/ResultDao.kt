package com.example.rockpaperscissors.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.rockpaperscissors.model.Result

@Dao
interface ResultDao {

    @Query("SELECT * FROM Result")
    suspend fun getAllResults(): List<Result>

    @Insert
    suspend fun insertResult(Result: Result)

    @Delete
    suspend fun deleteResult(Result: Result)

    @Query("DELETE FROM Result")
    suspend fun deleteAllResults()

    @Query("SELECT COUNT(*) FROM Result WHERE Result.winner = :winner")
    suspend fun getResultsByWinner(winner: String): Int
}