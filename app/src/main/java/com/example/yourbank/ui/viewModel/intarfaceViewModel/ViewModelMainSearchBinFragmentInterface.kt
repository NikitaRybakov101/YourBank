package com.example.yourbank.ui.viewModel.intarfaceViewModel

import androidx.lifecycle.MutableLiveData
import com.example.yourbank.ui.fragments.YourCardItem
import com.example.yourbank.ui.viewModel.StateData

interface ViewModelMainSearchBinFragmentInterface {
    fun getLiveData() : MutableLiveData<StateData>
    fun sendServerToCal(cardItem: YourCardItem)

    fun loadDataCardToDB()
    fun saveDataCardToDB(card : YourCardItem)
    fun deleteCard(cardItem: YourCardItem)

    fun saveDataCardToDbHistorySend(card : YourCardItem)
}