package com.example.yourbank.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yourbank.databinding.CardItemsBinding

class RecyclerYourCardAdapter(private val listYourCardItem: ArrayList<YourCardItem>) : RecyclerView.Adapter<RecyclerYourCardAdapter.ViewHolderYourCard>() {

    private var _binding : CardItemsBinding? = null
    private val binding : CardItemsBinding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerYourCardAdapter.ViewHolderYourCard {
        _binding = CardItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolderYourCard(binding.root,binding)
    }

    override fun onBindViewHolder(holder: RecyclerYourCardAdapter.ViewHolderYourCard, position: Int) {

        val yourCardItem = listYourCardItem[position]
        holder.itemRemoved()

        holder.textNameCard.text = yourCardItem.name
        holder.textBinCard.text = yourCardItem.bin
        holder.viewColorFilter.setBackgroundColor(yourCardItem.color)
    }

    override fun getItemCount(): Int {
        return listYourCardItem.size
    }

    inner class ViewHolderYourCard(view: View, binding: CardItemsBinding) : RecyclerView.ViewHolder(view) , InterfaceViewHolderNotes {
        val textNameCard = binding.textNameCard
        val viewColorFilter = binding.viewColorFilter
        val textBinCard = binding.textBinCard
        private val deletedCard = binding.deletedCard

        override fun itemRemoved() {
            deletedCard.setOnClickListener {
                listYourCardItem.removeAt(layoutPosition)
                notifyItemRemoved(layoutPosition)
            }
        }
    }

    interface InterfaceViewHolderNotes {
        fun itemRemoved()
    }
}