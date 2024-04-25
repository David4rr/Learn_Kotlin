package com.dicoding.asclepius.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.model.HistoryResponse

class HistoryAdapter(private val historyList: List<HistoryResponse>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPrediction: TextView = itemView.findViewById(R.id.tv_prediction)
        val tvConfidenceScore: TextView = itemView.findViewById(R.id.tv_confidenceScore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int = historyList.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentItem = historyList[position]
        holder.tvPrediction.text = currentItem.prediction
        holder.tvConfidenceScore.text = currentItem.confidenceScore.toString()
    }
}