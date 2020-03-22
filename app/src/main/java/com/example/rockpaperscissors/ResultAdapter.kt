package com.example.rockpaperscissors.database

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.model.Result
import kotlinx.android.synthetic.main.item_result.view.*

class ResultAdapter(private val results: ArrayList<Result>) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate( R.layout.item_result, parent, false)
        )
    }
    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(results[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(result : Result) {
            itemView.tvDate.text = result.date.toString()

            itemView.ivComputer.setImageResource(result.computer)


            when(result.winner){
                "Draw" -> itemView.tvResult.text = result.winner
                "Computer" -> itemView.tvResult.text = result.winner + " wins!"
                "You" -> itemView.tvResult.text = result.winner + " win!"
            }

            itemView.ivYou.setImageResource(result.user)
        }
    }
}