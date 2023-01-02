package com.example.yourbank.ui.fragments.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yourbank.databinding.CardItemsBinding
import com.example.yourbank.ui.fragments.CallbackBinInputInterfaceAndRecycler
import com.example.yourbank.ui.fragments.YourCardItem

class RecyclerYourCardAdapter(private val listYourCardItem: ArrayList<YourCardItem>, private val fragment: CallbackBinInputInterfaceAndRecycler) : RecyclerView.Adapter<RecyclerYourCardAdapter.ViewHolderYourCard>() {

    private var _binding : CardItemsBinding? = null
    private val binding : CardItemsBinding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderYourCard {
        _binding = CardItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolderYourCard(binding.root,binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderYourCard, position: Int) {

        val yourCardItem = listYourCardItem[position]
        holder.itemRemoved()
        holder.itemClicked()

        holder.textNameCard.text = yourCardItem.name
        holder.textBinCard.text = "BIN " + yourCardItem.bin
        holder.viewColorFilter.setBackgroundColor(yourCardItem.color)
    }

    override fun getItemCount(): Int {
        return listYourCardItem.size
    }

    inner class ViewHolderYourCard(view: View, binding: CardItemsBinding) : RecyclerView.ViewHolder(view) ,
        InterfaceViewHolderNotes {
        val textNameCard = binding.textNameCard
        val viewColorFilter = binding.viewColorFilter
        val textBinCard = binding.textBinCard
        private val deletedCard = binding.deletedCard
        private val materialCardViewYourCard = binding.materialCardViewYourCard

        override fun itemRemoved() {
            deletedCard.setOnClickListener {
                fragment.callbackRecyclerDeleteCard(listYourCardItem[layoutPosition])

                listYourCardItem.removeAt(layoutPosition)
                notifyItemRemoved(layoutPosition)
            }
        }

        override fun itemClicked() {
            materialCardViewYourCard.setOnClickListener {
                fragment.callbackRecycler(listYourCardItem[layoutPosition])
            }
        }
    }

    interface InterfaceViewHolderNotes {
        fun itemRemoved()
        fun itemClicked()
    }
}