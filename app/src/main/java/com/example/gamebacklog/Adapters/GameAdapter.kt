package com.example.gamebacklog.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamebacklog.Models.Game
import com.example.gamebacklog.R
import kotlinx.android.synthetic.main.item_game.view.*
import java.text.SimpleDateFormat

class GameAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(game : Game){
            itemView.tvGameTitle.text = game.title
            itemView.tvGamePlatform.text = game.platform

          var releaseDate : String =  "Release date : " +  SimpleDateFormat("d MMMM Y").format(game.releaseDate)
            itemView.tvReleaseDate.text =releaseDate

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false))
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(games[position])
    }


}