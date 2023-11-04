package com.inyonghengki.findinglocation.ui.tempat

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityProfiltempatBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.menufasilitas.ListMenuFasilitasActivity
import com.inyonghengki.findinglocation.ui.menuproduct.ListMenuProductActivity
import com.inyonghengki.findinglocation.ui.review.ListReviewPlaceMe
import com.inyonghengki.findinglocation.ui.tempat.adapter.TempatAdapter
import com.inyonghengki.findinglocation.ui.tempat.setting.SettingTempatActivity
import com.inyonghengki.findinglocation.util.Pref
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.toastError
import org.koin.androidx.viewmodel.ext.android.viewModel

class InfoTempatActivity : MyActivity() {

    private val viewModel : TempatViewModel by viewModel()
    private var adapter = TempatAdapter (
        onClick = {
            intentActivity(UpdatePlaceActivity::class.java,it)
        },

        onClickS = {
            intentActivity(SettingTempatActivity::class.java)
        },

    )

    val item = Tempat()

    private var _binding: ActivityProfiltempatBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfiltempatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Tempat Saya")

        mainButton()
        setupAdapter()
        getData()
    }

    private fun mainButton(){
        binding.apply {

            rvMenucekkode.setOnClickListener{
                intentActivity(QrCodeTempatActivity::class.java, item)
            }

            rvMenuproduct.setOnClickListener {
                intentActivity(ListMenuProductActivity::class.java)
            }

            rvMenufas.setOnClickListener {
                intentActivity(ListMenuFasilitasActivity::class.java)
            }

            rvUlasan.setOnClickListener {
                intentActivity(ListReviewPlaceMe::class.java)
            }

            rvTransaksi.setOnClickListener {
                intentActivity(ConfirmationBuyActivity::class.java)
            }
        }
    }

    override fun onResume() {
        getData()
        super.onResume()
    }

    private fun setupAdapter(){
        binding.rvData.adapter = adapter
    }

    val idUser = Pref.getUser()?.id

    private fun getData(){
        viewModel.get(idUser).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    val data = it.data ?: emptyList()
                    adapter.addItems(data)

                }
                State.ERROR -> {
                    toastError(it?.message ?: "Error")
                }
                State.LOADING -> {

                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}