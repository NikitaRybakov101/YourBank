package com.example.yourbank.ui.viewModel

import com.example.yourbank.dataBaseRoom.entities.YourSavedCardEntities
import com.example.yourbank.repository.ResponseDataYourCard
import com.example.yourbank.ui.fragments.YourCardItem

sealed class StateData {
    data class Success(val data: ResponseDataYourCard?, val cardItem: YourCardItem) : StateData()
    data class SuccessLoadingDB(val data: List<YourSavedCardEntities>) : StateData()

    data class Loading(val loading : String)     : StateData()
    data class Error  (val error   : Throwable)  : StateData()
}
