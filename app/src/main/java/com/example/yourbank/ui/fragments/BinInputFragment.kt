package com.example.yourbank.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.yourbank.R
import com.example.yourbank.databinding.BinInputFragmentBinding
import com.example.yourbank.ui.baseViewBinding.BaseViewBindingFragment
import com.example.yourbank.utils.showToast

class BinInputFragment : BaseViewBindingFragment<BinInputFragmentBinding>(BinInputFragmentBinding::inflate) {

    private lateinit var mainSearchBinFragment : CallbackBinInputInterfaceAndRecycler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkButton()
    }

    private fun checkButton() = with(binding) {
        buttonAddCard.setOnClickListener {
            createYourCard()
        }

        imageViewArrow.setOnClickListener {
            mainSearchBinFragment.callbackCloseInputBin()
        }
    }

    private fun createYourCard() = with(binding) {
        val bin = editText.text.toString()
        val userName = editTextNameUser.text.toString()

        if(bin.length == 8) {
            if(userName.isNotEmpty()) {
                mainSearchBinFragment.callbackBinInput(bin,userName)
            } else {
                editTextNameUser.error = getString(R.string.error_empty_field)
                context?.showToast(getString(R.string.error_mess_empty_field))
            }
        } else {
            editText.error = getString(R.string.number_error)
            context?.showToast(getString(R.string.error_mess))
        }
    }

    companion object {
        fun newInstance(mainSearchBinFragment: CallbackBinInputInterfaceAndRecycler) : BinInputFragment {
            val fragment = BinInputFragment()
            fragment.setListenerCallBack(mainSearchBinFragment)
            return fragment
        }
    }

    private fun setListenerCallBack(mainSearchBinFragment: CallbackBinInputInterfaceAndRecycler) {
        this.mainSearchBinFragment = mainSearchBinFragment
    }
}