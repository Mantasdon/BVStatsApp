package com.mantas.bvstatsapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mantas.bvstatsapp.R
import com.mantas.bvstatsapp.entity.Match

class MatchAdapter(private val matches: List<Match>) : RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    inner class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val matchName: TextView = itemView.findViewById(R.id.matchName)

        init {
            // Set up click listener (do something later)
            itemView.setOnClickListener {
                val match = matches[adapterPosition]
                // Future: Handle match click here
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matches[position]
        holder.matchName.text = match.name
    }

    override fun getItemCount(): Int = matches.size
}
