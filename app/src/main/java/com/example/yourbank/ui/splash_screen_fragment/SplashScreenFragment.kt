package com.example.yourbank.ui.splash_screen_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.yourbank.R
import com.example.yourbank.databinding.SplashScreenBinding
import com.example.yourbank.ui.baseViewBinding.BaseViewBindingFragment
import com.example.yourbank.ui.fragments.MainSearchBinFragment
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : BaseViewBindingFragment<SplashScreenBinding>(SplashScreenBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({

            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, MainSearchBinFragment.newInstance())
                .commit()

        }, 7000)

        uiScope.launch {
            loadingSplash()
        }

        binding.splashScreenCard.elevation = 15f
    }

    private fun loadingSplash() {
        binding.customTextView.setText(getString(R.string.app_name))
    }

    companion object {
        fun newInstance() = SplashScreenFragment()
    }

}