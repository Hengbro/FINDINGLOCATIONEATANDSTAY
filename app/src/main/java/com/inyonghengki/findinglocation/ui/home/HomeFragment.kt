package com.inyonghengki.findinglocation.ui.home

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.inyonghengki.findinglocation.core.data.source.model.CategoryFasilitas
import com.inyonghengki.findinglocation.core.data.source.model.MenuFasilitas
import com.inyonghengki.findinglocation.core.data.source.model.Pengunjung
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.FragmentHomeBinding
import com.inyonghengki.findinglocation.ui.base.MyFragment
import com.inyonghengki.findinglocation.ui.categoryfasilitas.CategotryFasilitaAdminViewModel
import com.inyonghengki.findinglocation.ui.home.adapter.CategoryAdapter
import com.inyonghengki.findinglocation.ui.home.adapter.PalceNewAdapter
import com.inyonghengki.findinglocation.ui.home.adapter.PlaceAgeAdapter
import com.inyonghengki.findinglocation.ui.home.adapter.PlaceByFasAdapter
import com.inyonghengki.findinglocation.ui.home.adapter.RekomendasiAdapter
import com.inyonghengki.findinglocation.ui.home.adapter.SliderAdapter
import com.inyonghengki.findinglocation.ui.searchplace.SearchActivity
import com.inyonghengki.findinglocation.ui.tempat.DetailTempatActivity
import com.inyonghengki.findinglocation.ui.tempat.TempatViewModel
import com.inyonghengki.findinglocation.util.Pref
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.setDefaultColor
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastError
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : MyFragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val viewModelTempat: TempatViewModel by viewModel()
    private val fasilitasViewModel: CategotryFasilitaAdminViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val adapterSlider = SliderAdapter()
    private val adapterRekomendasi = RekomendasiAdapter{
        detailTempat(it)
    }
    private val adapterNewPlace = PalceNewAdapter{
        detailTempat(it)
    }

    private val adapterPlaceAge = PlaceAgeAdapter{
        detailPlaceAge(it)
    }

    private val adapterPlaceByFas = PlaceByFasAdapter{
        detailPlaceByFas(it)
    }

    private val adapterCategory = CategoryAdapter()
    private var selectedFasilitasId: Int = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupAdepter()
        setupSlider()
        getHome()
        getCate()
        getSlider()
        setDataBaru()
        setPlaceAge()
        spinnerFas()
        setPlaceByFas()
        mainButton()

        return root
    }


    private fun setupAdepter(){
        binding.apply {
            rvTempatRekomend.adapter = adapterRekomendasi
            rvCategory.adapter = adapterCategory
            rvNewPlace.adapter = adapterNewPlace
            rvAgePlace.adapter = adapterPlaceAge
            rvFasilitas.adapter = adapterPlaceByFas
        }
    }

    private fun setupSlider() {
        binding.apply {
            slider.adapter = adapterSlider
            slider.setPadding(40, 0, 40, 0)
        }
    }

    private fun getSlider(){
        viewModel.getSlider().observe(requireActivity()){
            when (it.state) {
                State.SUCCESS -> {
                    binding.pdSlider.toGone()
                    binding.swipRefresh.isRefreshing = false
                    val data = it.data ?: emptyList()
                    adapterSlider.addItems(data)
                }
                State.ERROR -> {
                    binding.pdSlider.toGone()
                    toastError(it?.message ?: "Error")

                }
                State.LOADING -> {
                    binding.pdSlider.toVisible()
                }
            }
        }
    }

    private fun getCate(){
        viewModel.getCate().observe(requireActivity()){
            when (it.state) {
                State.SUCCESS -> {
                    binding.pdcategori.toGone()
                    binding.swipRefresh.isRefreshing = false

                    val data = it.data ?: emptyList()
                    adapterCategory.addItems(data)
                }
                State.ERROR -> {
                    binding.pdTempatRekomend.toGone()
                    toastError(it?.message ?: "Error")

                }
                State.LOADING -> {
                    binding.pdcategori.toVisible()
                }
            }
        }
    }

    private fun getHome(){
        viewModel.getHome().observe(requireActivity()){
            when (it.state) {
                State.SUCCESS -> {
                    binding.pdTempatRekomend.toGone()
                    binding.swipRefresh.isRefreshing = false

                    val data = it.data ?: emptyList()
                    adapterRekomendasi.addItems(data)
                }
                State.ERROR -> {
                    binding.pdTempatRekomend.toGone()
                    toastError(it?.message ?: "Error")

                }
                State.LOADING -> {
                    binding.pdTempatRekomend.toVisible()
                }
            }
        }
    }

    private fun setDataBaru(){
        viewModel.getNew().observe(requireActivity()) {
            when (it.state) {
                State.SUCCESS -> {
                    binding.pdNewTempat.toGone()
                    binding.swipRefresh.isRefreshing = false

                    val data = it.data ?: emptyList()
                    adapterNewPlace.addItems(data)

                }
                State.ERROR -> {
                    binding.pdNewTempat.toGone()
                    toastError(it?.message ?: "Error")
                }
                State.LOADING -> {
                    binding.pdNewTempat.toVisible()
                }
            }
        }
    }

    private fun setPlaceAge(){

        viewModel.getPlaceAge(Pref.getUser()?.id).observe(requireActivity()) {
            when (it.state) {
                State.SUCCESS -> {
                    binding.pdAgwPlace.toGone()
                    binding.swipRefresh.isRefreshing = false

                    val data = it.data ?: emptyList()
                    adapterPlaceAge.addItems(data)

                }
                State.ERROR -> {
                    binding.pdAgwPlace.toGone()
                    toastError(it?.message ?: "Error")
                }
                State.LOADING -> {
                    binding.pdAgwPlace.toVisible()
                }
            }
        }
    }

    private fun setPlaceByFas(){
        viewModel.getPlaceByFas(selectedFasilitasId).observe(requireActivity()) {
            when (it.state) {
                State.SUCCESS -> {
                    binding.pdFasilitas.toGone()
                    binding.swipRefresh.isRefreshing = false

                    val data = it.data ?: emptyList()
                    adapterPlaceByFas.addItems(data)

                }
                State.ERROR -> {
                    binding.pdFasilitas.toGone()
                    toastError(it?.message ?: "Error")
                }
                State.LOADING -> {
                    binding.pdFasilitas.toVisible()
                }
            }
        }
    }

    private fun spinnerFas(){
        fasilitasViewModel.get().observe(requireActivity()) {
            when (it.state) {
                State.SUCCESS -> {
                    binding.pdFasilitas.toGone()
                    binding.swipRefresh.isRefreshing = false

                    val fasilitasList = it.data // Ambil data fasilitas dari resource
                    if (fasilitasList != null) {
                        // Panggil fungsi untuk mengisi spinner dengan data fasilitas
                        populateSpinner(fasilitasList)
                    }
                }
                State.ERROR -> {
                    binding.pdFasilitas.toGone()
                    toastError(it?.message ?: "Error")
                }
                State.LOADING -> {
                    binding.pdFasilitas.toVisible()
                }
            }
        }
    }

    private fun populateSpinner(fasilitasList: List<CategoryFasilitas>) { // Ambil list nama fasilitas dari data fasilitas

        val spinnerAdapter = ArrayAdapter(requireActivity(), R.layout.simple_spinner_item, fasilitasList.map { it.name })
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = spinnerAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedMenuFasilitas = fasilitasList[position]
                selectedFasilitasId = selectedMenuFasilitas.id!!
                setPlaceByFas()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Tangani jika tidak ada item yang dipilih (opsional)
            }
        }
    }

    private fun mainButton(){

        binding.apply {

            swipRefresh.setDefaultColor()
            swipRefresh.setOnClickListener{
                getHome()
                getCate()
                getSlider()
                setDataBaru()
                setPlaceAge()
                spinnerFas()
                setPlaceByFas()

            }

            btnSearch.setOnClickListener {
                intentActivity(SearchActivity::class.java)
            }

            tvViewallplacenew.setOnClickListener {
                intentActivity(NewPlaceAllActivity::class.java)
            }
        }

    }

    private fun detailTempat(tempat: Tempat) {

        viewModelTempat.getOne(tempat.id).observe(requireActivity()){
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    intentActivity(DetailTempatActivity::class.java, it.data)
                }
                State.ERROR -> {
                    progress.dismiss()
                    toastError(it?.message ?: "Error")

                }
                State.LOADING -> {
                    progress.show()
                }
            }
        }
    }

    private fun detailPlaceAge(pengunjung: Pengunjung) {

        viewModelTempat.getOne(pengunjung.tempatId).observe(requireActivity()){
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    intentActivity(DetailTempatActivity::class.java, it.data)
                }
                State.ERROR -> {
                    progress.dismiss()
                    toastError(it?.message ?: "Error")

                }
                State.LOADING -> {
                    progress.show()
                }
            }
        }
    }

    private fun detailPlaceByFas(menufas: MenuFasilitas) {

        viewModelTempat.getOne(menufas.tempatId).observe(requireActivity()){
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    intentActivity(DetailTempatActivity::class.java, it.data)
                }
                State.ERROR -> {
                    progress.dismiss()
                    toastError(it?.message ?: "Error")

                }
                State.LOADING -> {
                    progress.show()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}