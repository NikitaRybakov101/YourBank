package com.example.yourbank.ui.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.yourbank.ui.fragments.YourCardItem

interface ViewModelMainSearchBinFragmentInterface {
    fun getLiveData() : MutableLiveData<StateData>
    fun sendServerToCal(cardItem: YourCardItem)

    fun loadDataCardToDB()
    fun saveDataCardToDB(card : YourCardItem)
    fun deleteNotes(cardItem: YourCardItem)
}