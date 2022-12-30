package com.example.yourbank.ui.fragments

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourbank.R
import com.example.yourbank.databinding.MainSearchBinFragmentBinding
import com.example.yourbank.ui.baseViewBinding.BaseViewBindingFragment
import java.util.Random

class MainSearchBinFragment : BaseViewBindingFragment<MainSearchBinFragmentBinding>(MainSearchBinFragmentBinding::inflate) , CallbackBinInputInterface {

    companion object {
        fun newInstance() = MainSearchBinFragment()
        private const val DURATION_ANIMATION = 1500L
    }

    private val listYourCardItem = ArrayList<YourCardItem>()
    private val recyclerView = RecyclerYourCardAdapter(listYourCardItem)

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
            if (binding.fragmentContainer.elevation == 0f) {
                openBinInput()
            }
        }
    }

    private fun openBinInput() = with(binding) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, BinInputFragment.newInstance(this@MainSearchBinFragment))
            .commit()

        fragmentContainer.elevation = 60f

        val viewY = ((root.height - fragmentContainer.height) / 8f)
        objectAnimation(viewY,DURATION_ANIMATION,60f)
    }

    private fun closeBinInput() = with(binding) {
        val viewY = 0f - fragmentContainer.height
        objectAnimation(viewY,DURATION_ANIMATION,0f)
    }

    private fun initListNotes() = with(binding) {
        recyclerAddedYouCard.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL,false)
        recyclerAddedYouCard.adapter = recyclerView
    }

    override fun callbackBinInput(bin: Int, userName: String) {
        closeBinInput()
        binding.customProgressBar.start()

        addItemToRecycler(bin,userName)
    }

    private fun addItemToRecycler(bin: Int, userName: String) {
        listYourCardItem.add(generateItem(bin,userName))
        recyclerView.notifyItemInserted(listYourCardItem.size + 1)
    }

    private fun generateItem(bin: Int, userName: String): YourCardItem {
        val random = Random()
        val color = Color.argb(255,random.nextInt(80) + 170,random.nextInt(80) + 170,random.nextInt(80) + 170)

        return YourCardItem(userName, color,"BIN $bin")
    }
}