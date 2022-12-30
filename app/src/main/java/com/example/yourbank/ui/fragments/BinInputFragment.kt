package com.example.yourbank.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.yourbank.R
import com.example.yourbank.databinding.BinInputFragmentBinding
import com.example.yourbank.ui.baseViewBinding.BaseViewBindingFragment

class BinInputFragment : BaseViewBindingFragment<BinInputFragmentBinding>(BinInputFragmentBinding::inflate) {

    private lateinit var mainSearchBinFragment : CallbackBinInputInterface

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkButton()
    }

    private fun checkButton() = with(binding) {
        buttonAddCard.setOnClickListener {

            val bin = editText.text.toString()
            val userName = editTextNameUser.text.toString()

            if(bin.length == 8) {
                if(userName.isNotEmpty()) {
                    mainSearchBinFragment.callbackBinInput(bin.toInt(),userName)
                } else {
                    editTextNameUser.error = getString(R.string.error_empty_field)
                    Toast.makeText(context,getString(R.string.error_mess_empty_field),Toast.LENGTH_SHORT).show()
                }
            } else {
                editText.error = getString(R.string.number_error)
                Toast.makeText(context,getString(R.string.error_mess),Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun newInstance(mainSearchBinFragment: CallbackBinInputInterface) : BinInputFragment {
            val fragment = BinInputFragment()
            fragment.setListenerCallBack(mainSearchBinFragment)
            return fragment
        }
    }

    private fun setListenerCallBack(mainSearchBinFragment: CallbackBinInputInterface) {
        this.mainSearchBinFragment = mainSearchBinFragment
    }
}