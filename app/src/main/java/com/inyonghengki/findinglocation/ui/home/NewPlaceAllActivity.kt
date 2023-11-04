package com.inyonghengki.findinglocation.ui.home

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityTempatbaruBinding
import com.inyonghengki.findinglocation.databinding.RegistertempatBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.home.adapter.RekomendasiAdapter
import com.inyonghengki.findinglocation.ui.tempat.DetailTempatActivity
import com.inyonghengki.findinglocation.ui.tempat.TempatViewModel
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastError
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewPlaceAllActivity : MyActivity() {

    private val viewModel: HomeViewModel by viewModel()
    private val viewModelTempat: TempatViewModel by viewModel()
    private val adapterNewPlace = RekomendasiAdapter{
        detailTempat(it)
    }

    private var _binding: ActivityTempatbaruBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTempatbaruBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Tempat Baru")

        setData()
        mainButton()
        setupAdepter()
    }

    private fun setupAdepter(){
        binding.apply {
            rvTempatRekomend.adapter = adapterNewPlace
        }
    }
    private fun mainButton(){

    }
    private fun setData(){
        viewModel.getNew().observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    binding.pdTempatRekomend.toGone()

                    val data = it.data ?: emptyList()
                    adapterNewPlace.addItems(data)

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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}