package com.example.rockpaperscissors.database

import android.content.Context
import com.example.rockpaperscissors.model.Result


class ResultRepo(context: Context) {

    private val resultDAO: ResultDao

    init {
        val database =
            ResultRoomDatabase.getDatabase(
                context
            )
        resultDAO = database!!.resultDAO()
    }

    suspend fun getAllResults(): List<Result> {
        return resultDAO.getAllResults()
    }

    suspend fun insertResult(result: Result) {
        resultDAO.insertResult(result)
    }

    suspend fun deleteResult(result: Result) {
        resultDAO.deleteResult(result)
    }

    suspend fun deleteAllResults() {
        resultDAO.deleteAllResults()
    }

    suspend fun getResultsByWinner(winner: String): Int {
        return resultDAO.getResultsByWinner(winner)
    }
}