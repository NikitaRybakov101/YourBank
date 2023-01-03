package com.example.yourbank.di

import androidx.room.Room
import com.example.yourbank.dataBaseRoom.DataBase
import com.example.yourbank.dataBaseRoom.dao.DaoDbYourCard
import com.example.yourbank.repository.RetrofitImpl
import com.example.yourbank.ui.viewModel.ViewModelHistoryFragment
import com.example.yourbank.ui.viewModel.ViewModelMainSearchBinFragment
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val applicationModule = module {

    single {
        Room.databaseBuilder(androidContext(), DataBase::class.java, DATA_BASE_NAME).fallbackToDestructiveMigration().build()
    }
    single(named(DATA_BASE)) {
        get<DataBase>().dataBase()
    }

    single(named(RETROFIT_IMPL)) {
        RetrofitImpl()
    }
}


val viewModelModule = module {
    viewModel(named(VIEW_MODEL)) { (retrofit: RetrofitImpl, dao : DaoDbYourCard) ->
        ViewModelMainSearchBinFragment(retrofit,dao,androidContext())
    }

    viewModel(named(VIEW_MODEL_HISTORY)) { (dao : DaoDbYourCard) ->
        ViewModelHistoryFragment(dao)
    }
}


