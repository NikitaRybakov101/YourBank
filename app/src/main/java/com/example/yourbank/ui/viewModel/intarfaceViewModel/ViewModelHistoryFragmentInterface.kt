package com.example.yourbank.ui.viewModel.intarfaceViewModel

import androidx.lifecycle.MutableLiveData
import com.example.yourbank.ui.viewModel.StateData

interface ViewModelHistoryFragmentInterface {
    fun getLiveData() : MutableLiveData<StateData>
    fun loadDataCardToDbHistory()
    fun deleteAllHistory()
}