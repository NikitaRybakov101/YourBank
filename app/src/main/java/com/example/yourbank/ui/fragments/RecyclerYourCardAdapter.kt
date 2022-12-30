package com.example.yourbank.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yourbank.databinding.CardItemsBinding

class RecyclerYourCardAdapter() : RecyclerView.Adapter<RecyclerYourCardAdapter.ViewHolderYourCard>() {

    private var _binding : CardItemsBinding? = null
    private val binding : CardItemsBinding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerYourCardAdapter.ViewHolderYourCard {
        _binding = CardItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolderYourCard(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerYourCardAdapter.ViewHolderYourCard, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class ViewHolderYourCard(view : View) : RecyclerView.ViewHolder(view) , InterfaceViewHolderNotes {

        override fun itemRemoved(){

        }
    }

    interface InterfaceViewHolderNotes {
        fun itemRemoved()
    }
}