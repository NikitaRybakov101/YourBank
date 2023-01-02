package com.example.yourbank.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yourbank.dataBaseRoom.dao.DaoDbYourCard
import com.example.yourbank.dataBaseRoom.entities.HistorySendEntities
import com.example.yourbank.dataBaseRoom.entities.YourSavedCardEntities
import com.example.yourbank.repository.RetrofitImpl
import com.example.yourbank.ui.fragments.YourCardItem
import com.example.yourbank.ui.viewModel.intarfaceViewModel.ViewModelMainSearchBinFragmentInterface
import kotlinx.coroutines.*

class ViewModelMainSearchBinFragment(private val retrofit: RetrofitImpl, private val daoDbYourCard: DaoDbYourCard) : ViewModel() , ViewModelMainSearchBinFragmentInterface {

    private val liveData = MutableLiveData<StateData>()
    override fun getLiveData() = liveData

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun sendServerToCal(cardItem: YourCardItem) {

        liveData.value = StateData.Loading("Loading")

        scope.launch {
            kotlin.runCatching {

                delay(1500)
                val response = retrofit.getRetrofit().getDataCardToBin(cardItem.bin).execute()

                response
            }.onSuccess { response ->

                (Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null){
                        liveData.value = StateData.Success(response.body(),cardItem)
                    } else {
                        liveData.value = StateData.Error(Throwable("NetWork ERROR body"))
                    }
                }
            }.onFailure {
                (Dispatchers.Main) {
                    liveData.value = StateData.Error(Throwable("NetWork ERROR"))
                }
            }
        }
    }

    override fun loadDataCardToDB() {
        scope.launch {
            val listCard = daoDbYourCard.getAllSavedCard()

            withContext(Dispatchers.Main) {
                liveData.value = StateData.SuccessLoadingDB(listCard)
            }
        }
    }

    override fun saveDataCardToDB(card : YourCardItem)  {
        scope.launch {
            daoDbYourCard.insertCard(YourSavedCardEntities(bin = card.bin, nameUser = card.name, color = card.color))
        }
    }

    override fun saveDataCardToDbHistorySend(card : YourCardItem)  {
        scope.launch {
            daoDbYourCard.insertHistorySend(HistorySendEntities(bin = card.bin, nameUser = card.name, time = "time"))
        }
    }

    override fun deleteCard(cardItem: YourCardItem) {
        scope.launch {
            daoDbYourCard.deleteSavedCard(cardItem.bin)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}