package com.example.yourbank.ui.fragments

interface CallbackBinInputInterfaceAndRecycler {
    fun callbackBinInput(bin: String, userName: String)
    fun callbackCloseInputBin()
    fun callbackRecycler(cardItem: YourCardItem)
    fun callbackRecyclerDeleteCard(cardItem: YourCardItem)
}