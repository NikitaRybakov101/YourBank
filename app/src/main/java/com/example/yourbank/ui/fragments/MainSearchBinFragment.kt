package com.example.yourbank.ui.fragments

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourbank.R
import com.example.yourbank.dataBaseRoom.dao.DaoDbYourCard
import com.example.yourbank.dataBaseRoom.entities.YourSavedCardEntities
import com.example.yourbank.databinding.MainSearchBinFragmentBinding
import com.example.yourbank.di.DATA_BASE
import com.example.yourbank.di.RETROFIT_IMPL
import com.example.yourbank.di.VIEW_MODEL
import com.example.yourbank.repository.ResponseDataYourCard
import com.example.yourbank.repository.RetrofitImpl
import com.example.yourbank.ui.baseViewBinding.BaseViewBindingFragment
import com.example.yourbank.ui.fragments.recycler.RecyclerYourCardAdapter
import com.example.yourbank.ui.viewModel.StateData
import com.example.yourbank.ui.viewModel.ViewModelMainSearchBinFragment
import com.example.yourbank.utils.showToast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import java.util.Random

class MainSearchBinFragment : BaseViewBindingFragment<MainSearchBinFragmentBinding>(MainSearchBinFragmentBinding::inflate) , CallbackBinInputInterfaceAndRecycler {

    companion object {
        fun newInstance() = MainSearchBinFragment()

        private const val DURATION_ANIMATION = 1500L
        private const val ELEVATION_CARD_INPUT_BIN = 120f
        private const val YOUR_CARD_ELEVATION = 28f
        private const val CONTAINER_CARD_ELEVATION = 15f
    }

    private val listYourCardItem = ArrayList<YourCardItem>()
    private val recyclerView = RecyclerYourCardAdapter(listYourCardItem,this)

    private val retrofit : RetrofitImpl by inject(named(RETROFIT_IMPL))
    private val notesDao: DaoDbYourCard by inject(named(DATA_BASE)) { parametersOf(requireActivity()) }

    private val viewModel : ViewModelMainSearchBinFragment by viewModel(named(VIEW_MODEL))  {
        parametersOf(retrofit,notesDao)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        initListNotes()
        initView()
        checkButton()
    }

    private fun initViewModel() { viewModel.getLiveData().observe(viewLifecycleOwner) { render(it) } }

    private fun render(stateData: StateData) {
        when(stateData) {
            is StateData.Loading -> {
                binding.customProgressBar.start()
            }
            is StateData.Success -> {
                binding.customProgressBar.stop()

                stateData.data?.let {
                    setDataToYourCard(it, stateData.cardItem)
                    context?.showToast(getString(R.string.success_mess))
                }

            }
            is StateData.Error -> {
                binding.customProgressBar.stop()
                context?.showToast(getString(R.string.error_network_mess))
            }
            is StateData.SuccessLoadingDB -> {
                loadingCardToDataBase(stateData.data)
            }
        }
    }

    private fun loadingCardToDataBase(listCard : List<YourSavedCardEntities>) {

        if(listCard.isEmpty()) {
            openBinInput()
        } else {
            listCard.forEach { card ->
                addItemToRecycler(card.bin, card.nameUser)
            }
            viewModel.sendServerToCal(generateItem(listCard[0].bin,listCard[0].nameUser))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDataToYourCard(responseDataYourCard: ResponseDataYourCard, cardItem: YourCardItem) = with(binding) {

        val nameBank = responseDataYourCard.bank.name

        if(nameBank != null) {
            textNameBank.text = nameBank
        } else {
            textNameBank.text = getString(R.string.some_bank)
        }

        textBrand.alpha = 1f
        textType.alpha = 1f

        responseDataYourCard.apply {

            textCountry.text = getString(R.string.your_country)
            textBrandCard.text = brand
            textTypeCard.text = type

            textCardNumber.text = getString(R.string.card_num_length) + "  " + number.length
            textPrepaid.text = getString(R.string.prepaid) + "  " + prepaid

            textCountryName.text = country.name

            textYourName.text = cardItem.name
            textBin.text = getString(R.string.bin) + "  " + cardItem.bin
            textYourNameCard.text = cardItem.name

            textWeb.text = getString(R.string.website) + "  " + bank.url
            textPhone.text = getString(R.string.phone) + "  " + bank.phone
            textCountryInfo.text = getString(R.string.country_info) + "  " + country.name
        }
    }

    private fun initView() = with(binding) {
        yourCardView.elevation = YOUR_CARD_ELEVATION
        materialCardViewContainerYourCard.elevation = CONTAINER_CARD_ELEVATION
        fragmentContainer.elevation = 0f

        customProgressBar.stop()

        viewModel.loadDataCardToDB()
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

        fragmentContainer.elevation = ELEVATION_CARD_INPUT_BIN

        val viewY = ((root.height - fragmentContainer.height) / 16f)
        objectAnimation(viewY,DURATION_ANIMATION,ELEVATION_CARD_INPUT_BIN)
    }

    private fun closeBinInput() = with(binding) {
        val viewY = 0f - fragmentContainer.height
        objectAnimation(viewY,DURATION_ANIMATION,0f)
    }

    private fun initListNotes() = with(binding) {
        recyclerAddedYouCard.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL,false)
        recyclerAddedYouCard.adapter = recyclerView
    }

    override fun callbackBinInput(bin: String, userName: String) {
        closeBinInput()
        addItemToRecycler(bin,userName)

        viewModel.saveDataCardToDB(generateItem(bin,userName))
    }

    override fun callbackCloseInputBin() {
        closeBinInput()
    }

    override fun callbackRecycler(cardItem: YourCardItem) {
        viewModel.sendServerToCal(cardItem)
    }

    override fun callbackRecyclerDeleteCard(cardItem: YourCardItem) {
        viewModel.deleteNotes(cardItem)
    }

    private fun addItemToRecycler(bin: String, userName: String) {
        val cardItem = generateItem(bin,userName)

        listYourCardItem.add(cardItem)
        recyclerView.notifyItemInserted(listYourCardItem.size + 1)

        viewModel.sendServerToCal(cardItem)
    }

    private fun generateItem(bin: String, userName: String): YourCardItem {
        val random = Random()
        val color = Color.argb(255,random.nextInt(80) + 170,random.nextInt(80) + 170,random.nextInt(80) + 170)

        return YourCardItem(userName, color,bin)
    }
}