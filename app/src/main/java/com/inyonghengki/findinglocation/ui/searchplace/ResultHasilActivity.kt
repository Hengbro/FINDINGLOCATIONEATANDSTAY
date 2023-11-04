package com.inyonghengki.findinglocation.ui.searchplace

import android.annotation.SuppressLint
import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivitySearchPlaceFinishBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.home.adapter.RekomendasiAdapter
import com.inyonghengki.findinglocation.ui.menuproduct.MenuProductViewModel
import com.inyonghengki.findinglocation.ui.tempat.DetailTempatActivity
import com.inyonghengki.findinglocation.ui.tempat.TempatViewModel
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastError
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("MissingInflatedId", "SetTextI18n")
class ResultHasilActivity : MyActivity() {

    private lateinit var  binding: ActivitySearchPlaceFinishBinding
    private val viewModelPlace: MenuProductViewModel by viewModel()
    private val viewModelTempat: TempatViewModel by viewModel()
    private val cariadapter = RekomendasiAdapter {
        detailTempat(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchPlaceFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainButton()
        setCari()
        setupAdepter()
        setDataProduk()
    }

    private fun setupAdepter(){
        binding.rvTempatRekomend.adapter = cariadapter
    }

    private fun setCari(){
        val messager = intent.getStringExtra("themessage")
        binding.tvSearch.apply {
            text = messager
        }
    }

    private fun setDataProduk(){
        val reqData = binding.tvSearch.text.toString()

        viewModelPlace.getFind(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    binding.pdTempatRekomend.toGone()
                    val data = it.data ?: emptyList()
                    cariadapter.addItems(data)
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

    private fun detailTempat(tempat: Tempat) {

        viewModelTempat.getOne(tempat.id).observe(this){
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

    private fun mainButton(){
        binding.apply {
            btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            lyCari.setOnClickListener {
                intentActivity(SearchActivity::class.java)
            }
        }
    }
}
