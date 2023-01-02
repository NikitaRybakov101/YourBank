package com.example.yourbank.ui.fragments.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yourbank.dataBaseRoom.entities.HistorySendEntities
import com.example.yourbank.databinding.CardItemHistoryBinding

class RecyclerHistoryAdapter(val listHistory : ArrayList<HistorySendEntities>) : RecyclerView.Adapter<RecyclerHistoryAdapter.ViewHolderHistory>() {

    private var _binding : CardItemHistoryBinding? = null
    private val binding : CardItemHistoryBinding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHistory {
        _binding = CardItemHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolderHistory(binding.root,binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderHistory, position: Int) {

        holder.binCode.text = "BIN " + listHistory[position].bin
        holder.textTime.text = "time 21/12/2023"
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    inner class ViewHolderHistory(view: View, binding: CardItemHistoryBinding) : RecyclerView.ViewHolder(view)  {
        val binCode = binding.binCode
        val textTime = binding.textTime
        private val cardItemHistory = binding.cardItemHistory

        init {
            cardItemHistory.elevation = 5f
        }
    }
}