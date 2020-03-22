package com.example.rockpaperscissors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.database.ResultAdapter
import com.example.rockpaperscissors.database.ResultRepo
import kotlinx.android.synthetic.main.activity_history.*
import com.example.rockpaperscissors.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {

    private lateinit var resultRepository: ResultRepo
    private val resultHistory = arrayListOf<Result>()
    private val resultAdapter = ResultAdapter(resultHistory)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        resultRepository = ResultRepo(this)
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        menu?.findItem(R.id.action_history)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        menu?.findItem(R.id.action_history)?.isVisible = false
        menu?.findItem(R.id.action_delete)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

        return super.onCreateOptionsMenu(menu)
    }

    private fun initViews() {
        rvResults.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvResults.adapter = resultAdapter
        rvResults.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        refreshHistory()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_delete -> {
            CoroutineScope(Dispatchers.Main).launch {
                resultRepository.deleteAllResults()
            }
            Toast.makeText(this@HistoryActivity,"History deleted",
                Toast.LENGTH_LONG).show()
            refreshHistory()
            true
        }
        android.R.id.home ->{
            this.finish()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun refreshHistory() {
        CoroutineScope(Dispatchers.Main).launch {
            val resultHistory = withContext(Dispatchers.IO) {
                resultRepository.getAllResults()
            }
            this@HistoryActivity.resultHistory.clear()
            this@HistoryActivity.resultHistory.addAll(resultHistory)
            this@HistoryActivity.resultAdapter.notifyDataSetChanged()

        }
    }

}