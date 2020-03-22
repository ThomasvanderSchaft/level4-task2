package com.example.rockpaperscissors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.rockpaperscissors.database.ResultRepo
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.item_result.ivComputer
import kotlinx.android.synthetic.main.item_result.ivYou
import kotlinx.android.synthetic.main.item_result.tvResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.util.*
import com.example.rockpaperscissors.model.Result



class MainActivity : AppCompatActivity() {

    private lateinit var resultRepo: ResultRepo
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultRepo = ResultRepo(this)

        initViews()
    }

    private fun initViews() {
        tvResult.text = ""

        getStatistics()

        btnRock.setOnClickListener {
            ivYou.setImageResource(R.drawable.rock)
            play(R.drawable.rock)
        }
        btnPaper.setOnClickListener {
            ivYou.setImageResource(R.drawable.paper)
            play(R.drawable.paper)
        }
        btnScissors.setOnClickListener {
            ivYou.setImageResource(R.drawable.scissors)
            play(R.drawable.scissors)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        menu?.findItem(R.id.action_history)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        menu?.findItem(R.id.action_delete)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        menu?.findItem(R.id.action_delete)?.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_history -> {
            startActivity(Intent(this, HistoryActivity::class.java))
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun updateStatistics(win: Int, draw: Int, lose: Int) {
        tvStats.text = "Win: $win Draw: " + "$draw Lose: $lose"
    }

    private fun play(resId: Int) {
        when(resId) {
            R.drawable.rock -> {
                when (computerMove()) {
                    R.drawable.rock -> {
                        tvResult.text = getString(R.string.draw)
                        submit(R.drawable.rock, resId, "Draw")
                    }
                    R.drawable.paper -> {
                        tvResult.text = getString(R.string.lose)
                        submit(R.drawable.paper, resId, "Computer")
                    }
                    else -> {
                        tvResult.text = getString(R.string.win)
                        submit(R.drawable.scissors, resId, "You")
                    }
                }
            }
            R.drawable.scissors -> {
                when (computerMove()) {
                    R.drawable.rock -> {
                        tvResult.text = getString(R.string.lose)
                        submit(R.drawable.rock, resId, "Computer")
                    }
                    R.drawable.paper -> {
                        tvResult.text = getString(R.string.win)
                        submit(R.drawable.paper, resId, "You")
                    }
                    else -> {
                        tvResult.text = getString(R.string.draw)
                        submit(R.drawable.scissors, resId, "Draw")
                    }
                }
            }
            R.drawable.paper -> {
                when (computerMove()) {
                    R.drawable.rock -> {
                        tvResult.text = getString(R.string.win)
                        submit(R.drawable.rock, resId, "You")
                    }
                    R.drawable.paper -> {
                        tvResult.text = getString(R.string.draw)
                        submit(R.drawable.paper, resId, "Draw")
                    }
                    else -> {
                        tvResult.text = getString(R.string.lose)
                        submit(R.drawable.scissors, resId, "Computer")
                    }
                }
            }
            else -> {
                Toast.makeText(this, "Something went wrong! Try again.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun computerMove() : Int {
        when((0..2).random()) {
            0 -> {
                ivComputer.setImageResource(R.drawable.rock)
                return R.drawable.rock
            }
            1 -> {
                ivComputer.setImageResource(R.drawable.scissors)
                return R.drawable.scissors
            }
            2 -> {
                ivComputer.setImageResource(R.drawable.paper)
                return R.drawable.paper
            }
        }

        return -1
    }
    private fun getStatistics() {
        var you = 0
        var draw = 0
        var computer = 0

        mainScope.launch {
            you = resultRepo.getResultsByWinner("You")
            draw = resultRepo.getResultsByWinner("Draw")
            computer = resultRepo.getResultsByWinner("Computer")
        }.invokeOnCompletion {
            updateStatistics(you, draw, computer)
        }
    }

    private fun submit(computer: Int, user: Int, win: String) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                resultRepo.insertResult(Result(null, user, computer, win, Date.from(Instant.now())))
            }
        }.invokeOnCompletion {
            getStatistics()
        }
    }
}
