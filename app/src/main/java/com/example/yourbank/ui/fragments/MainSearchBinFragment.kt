package com.example.yourbank.ui.fragments

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
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
import java.util.*

class MainSearchBinFragment : BaseViewBindingFragment<MainSearchBinFragmentBinding>(MainSearchBinFragmentBinding::inflate) , CallbackBinInputInterfaceAndRecycler {

    companion object {
        fun newInstance() = MainSearchBinFragment()

        private const val DURATION_ANIMATION = 1500L
        private const val ELEVATION_CARD_INPUT_BIN = 120f
        private const val YOUR_CARD_ELEVATION = 28f
        private const val CONTAINER_CARD_ELEVATION = 5f
        private const val CONTAINER_CARD_PADDING = 50
        private const val TEXT_SIZE_YOUR_NAME = 90f
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
            else -> {}
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

            textCardNumber.text = getString(R.string.card_num_length) + number.length
            textPrepaid.text = getString(R.string.prepaid) + prepaid

            textCountryName.text = country.name

            textYourName.text = cardItem.name
            textBin.text = getString(R.string.bin) + cardItem.bin

            textYourNameCard.setText(cardItem.name,"null",TEXT_SIZE_YOUR_NAME)

            textWeb.text = getString(R.string.website) + bank.url
            textPhone.text = getString(R.string.phone) + bank.phone
            textCountryInfo.text = getString(R.string.country_info) + country.name
            setClickableInfo(this)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setClickableInfo(card: ResponseDataYourCard) = with(binding)  {
        textWeb.text = getString(R.string.website) + card.bank.url
        textWeb.setOnClickListener {
            searchWeb(card.bank.url)
        }

        textPhone.text = getString(R.string.phone) + card.bank.phone
        textPhone.setOnClickListener {
            permissionCallPhone(card.bank.phone)
        }

        textCountryInfo.text = getString(R.string.country_info) + card.country.name
        textCountryInfo.setOnClickListener {
            openMap(card.country.latitude,card.country.longitude)
        }
    }

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissionCallPhone(binding.textPhone.text.toString().replace(getString(R.string.phone),"")) }
    private fun requestLocationPermissions() = permissionLauncher.launch(Manifest.permission.CALL_PHONE)

    private fun permissionCallPhone(phone: String) {
        if(ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            callPhone(phone)
        } else {
            requestLocationPermissions()
        }
    }

    private fun callPhone(phone: String) {
        val dialIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone"))
        startActivity(dialIntent)
    }

    private fun searchWeb(url: String) {
        val uri = Uri.parse("https://$url")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun openMap(latitude : String, longitude : String ) {
        val uri = "geo:$latitude,$longitude"
        val mapIntent =  Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(mapIntent)
    }

    private fun initView() = with(binding) {
        yourCardView.elevation = YOUR_CARD_ELEVATION
        materialCardViewContainerYourCard.elevation = CONTAINER_CARD_ELEVATION
        fragmentContainer.elevation = ELEVATION_CARD_INPUT_BIN

        customProgressBar.stop()
        textYourNameCard.setText(getString(R.string.your_name),"null",TEXT_SIZE_YOUR_NAME)

        viewModel.loadDataCardToDB()
    }

    private fun objectAnimation(value : Float, durationTime: Long) {
        ObjectAnimator.ofFloat(binding.fragmentContainer, View.Y, value).apply {
            duration = durationTime
            interpolator = AnticipateOvershootInterpolator(1f)
            start()
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

        val viewY = ((root.height - fragmentContainer.height + convertDpToPixels(CONTAINER_CARD_PADDING)) / 16f)
        objectAnimation(viewY,DURATION_ANIMATION)
    }

    private fun closeBinInput() = with(binding) {
        val viewY = 0f - fragmentContainer.height - convertDpToPixels(CONTAINER_CARD_PADDING)
        objectAnimation(viewY,DURATION_ANIMATION)
    }

    private fun initListNotes() = with(binding) {
        recyclerAddedYouCard.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL,false)
        recyclerAddedYouCard.adapter = recyclerView
    }

    override fun callbackBinInput(bin: String, userName: String) {
        closeBinInput()
        addItemToRecycler(bin,userName)

        val cardItem = generateItem(bin,userName)

        viewModel.sendServerToCal(cardItem)
        viewModel.saveDataCardToDbHistorySend(cardItem)
        viewModel.saveDataCardToDB(generateItem(bin,userName))
    }

    override fun callbackCloseInputBin() {
        closeBinInput()
    }

    override fun callbackRecycler(cardItem: YourCardItem) {
        viewModel.sendServerToCal(cardItem)
        viewModel.saveDataCardToDbHistorySend(cardItem)
    }

    override fun callbackRecyclerDeleteCard(cardItem: YourCardItem) {
        viewModel.deleteCard(cardItem)
    }

    private fun addItemToRecycler(bin: String, userName: String) {
        val cardItem = generateItem(bin,userName)

        listYourCardItem.add(cardItem)
        recyclerView.notifyItemInserted(listYourCardItem.size + 1)
    }

    private fun convertDpToPixels(dp: Int) = (dp * requireContext().resources.displayMetrics.density).toInt()

    private fun generateItem(bin: String, userName: String): YourCardItem {
        val random = Random()
        val color = Color.argb(255,random.nextInt(80) + 170,random.nextInt(80) + 170,random.nextInt(80) + 170)

        return YourCardItem(userName, color,bin)
    }
}