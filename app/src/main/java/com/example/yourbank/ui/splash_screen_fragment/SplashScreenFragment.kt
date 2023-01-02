package com.example.yourbank.ui.splash_screen_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.yourbank.R
import com.example.yourbank.databinding.SplashScreenBinding
import com.example.yourbank.ui.baseViewBinding.BaseViewBindingFragment
import com.example.yourbank.ui.fragments.MainSearchBinFragment
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : BaseViewBindingFragment<SplashScreenBinding>(SplashScreenBinding::inflate) {

    companion object {
        private const val URL_FONT = "fonts/zabal.otf"
        private const val TEXT_SIZE_APP_NAME = 110f
        private const val TIME_ANIMATION_MILLIS = 7000L
        private const val CARD_ELEVATION = 15f
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashScreenFragment_to_MainSearchBinFragment)
        }, TIME_ANIMATION_MILLIS)

        uiScope.launch {
            loadingSplash()
        }

        binding.splashScreenCard.elevation = CARD_ELEVATION
    }

    private fun loadingSplash() {
        binding.customTextView.setText(getString(R.string.app_name),URL_FONT,TEXT_SIZE_APP_NAME)
    }
}