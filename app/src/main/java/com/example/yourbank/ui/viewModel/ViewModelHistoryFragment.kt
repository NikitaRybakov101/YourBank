package com.example.yourbank.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yourbank.dataBaseRoom.dao.DaoDbYourCard
import com.example.yourbank.dataBaseRoom.entities.HistorySendEntities
import com.example.yourbank.dataBaseRoom.entities.YourSavedCardEntities
import com.example.yourbank.repository.RetrofitImpl
import com.example.yourbank.ui.fragments.YourCardItem
import com.example.yourbank.ui.viewModel.intarfaceViewModel.ViewModelHistoryFragmentInterface
import com.example.yourbank.ui.viewModel.intarfaceViewModel.ViewModelMainSearchBinFragmentInterface
import kotlinx.coroutines.*

class ViewModelHistoryFragment(private val daoDbYourCard: DaoDbYourCard) : ViewModel() , ViewModelHistoryFragmentInterface {

    private val liveData = MutableLiveData<StateData>()
    override fun getLiveData() = liveData

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun loadDataCardToDbHistory() {
        scope.launch {
            val listCard = daoDbYourCard.getAllHistorySend()

            withContext(Dispatchers.Main) {
                liveData.value = StateData.SuccessLoadingDbHistory(listCard)
            }
        }
    }

    override fun deleteAllHistory() {
        scope.launch {
            daoDbYourCard.deleteAllHistory()
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}