package com.example.yourbank.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourbank.dataBaseRoom.dao.DaoDbYourCard
import com.example.yourbank.dataBaseRoom.entities.HistorySendEntities
import com.example.yourbank.databinding.HistoryFragmentBinding
import com.example.yourbank.di.DATA_BASE
import com.example.yourbank.di.VIEW_MODEL_HISTORY
import com.example.yourbank.ui.baseViewBinding.BaseViewBindingFragment
import com.example.yourbank.ui.fragments.recycler.RecyclerHistoryAdapter
import com.example.yourbank.ui.viewModel.StateData
import com.example.yourbank.ui.viewModel.ViewModelHistoryFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class HistoryFragment : BaseViewBindingFragment<HistoryFragmentBinding>(HistoryFragmentBinding::inflate)  {

    private val notesDao: DaoDbYourCard by inject(named(DATA_BASE)) { parametersOf(requireActivity()) }
    private val viewModel : ViewModelHistoryFragment by viewModel(named(VIEW_MODEL_HISTORY))  {
        parametersOf(notesDao)
    }

    private lateinit var recyclerView : RecyclerHistoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        checkButton()
        viewModel.loadDataCardToDbHistory()
    }

    private fun checkButton() = with(binding) {
        deleteHistory.setOnClickListener {
            deleteAllHistory()
        }
    }

    private fun initViewModel() { viewModel.getLiveData().observe(viewLifecycleOwner) { render(it) } }

    private fun render(stateData: StateData) {
        when(stateData) {
            is StateData.SuccessLoadingDbHistory -> {
                createListHistory(stateData.data)
            }
            else -> {}
        }
    }

    private fun createListHistory(history : List<HistorySendEntities>) {
        recyclerView = RecyclerHistoryAdapter(history as ArrayList<HistorySendEntities>)
        initListHistory()
    }

    private fun deleteAllHistory() {
        viewModel.deleteAllHistory()

        val itemCount = recyclerView.listHistory.size
        recyclerView.listHistory.clear()
        recyclerView.notifyItemRangeRemoved(0,itemCount)
    }

    private fun initListHistory() = with(binding) {
        recyclerHistory.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
        recyclerHistory.adapter = recyclerView
    }
}