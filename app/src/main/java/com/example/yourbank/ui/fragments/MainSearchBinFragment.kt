package com.example.yourbank.ui.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourbank.R
import com.example.yourbank.databinding.MainSearchBinFragmentBinding
import com.example.yourbank.ui.baseViewBinding.BaseViewBindingFragment

class MainSearchBinFragment : BaseViewBindingFragment<MainSearchBinFragmentBinding>(MainSearchBinFragmentBinding::inflate) , CallbackBinInputInterface {

    companion object {
        fun newInstance() = MainSearchBinFragment()
        private const val DURATION_ANIMATION_CONSTRUCTOR = 1500L
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListNotes()
        initView()
        checkButton()
    }

    private fun initView() = with(binding) {
        yourCardView.elevation = 28f
        materialCardViewContainerYourCard.elevation = 15f
        fragmentContainer.elevation = 0f

        customProgressBar.stop()
    }

    private fun objectAnimation(value : Float, durationTime: Long, elevation : Float) {
        ObjectAnimator.ofFloat(binding.fragmentContainer, View.Y, value).apply {
            duration = durationTime
            interpolator = AnticipateOvershootInterpolator(1f)
            start()
        }.doOnEnd {
            binding.fragmentContainer.elevation = elevation
        }
    }

    private fun checkButton() = with(binding){
        chipAddedCard.setOnClickListener {
            openBinInput()
        }
    }

    private fun openBinInput() = with(binding) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, BinInputFragment.newInstance(this@MainSearchBinFragment))
            .commit()

        fragmentContainer.elevation = 60f

        val viewY = ((root.height - fragmentContainer.height) / 8f)
        objectAnimation(viewY,DURATION_ANIMATION_CONSTRUCTOR,60f)
    }

    private fun closeBinInput() = with(binding) {
        val viewY = 0f - fragmentContainer.height
        objectAnimation(viewY,DURATION_ANIMATION_CONSTRUCTOR,0f)
    }

    private fun initListNotes() = with(binding) {
        recyclerAddedYouCard.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL,false)
        recyclerAddedYouCard.adapter = RecyclerYourCardAdapter()
    }

    override fun callbackBinInput(bin: Int) {
        closeBinInput()
        binding.customProgressBar.start()
    }
}