package com.inyonghengki.findinglocation.ui.tempat

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.KeranjangTempat
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityCekfakturplaceBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.feedback.FeedbackViewModel
import com.inyonghengki.findinglocation.ui.feedback.adapter.ViewCartFakturAdapter
import com.inyonghengki.findinglocation.ui.keranjangproduct.KeranjangProductViewModel
import com.inyonghengki.findinglocation.util.Pref
import com.inyongtisto.myhelper.extension.extra
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toRupiah
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastError
import org.koin.androidx.viewmodel.ext.android.viewModel

class TypesOrderByPlaceActivity : MyActivity() {

    private val viewModelProduct : KeranjangProductViewModel by viewModel()
    private val viewModel : FeedbackViewModel by viewModel()
    private val adapter = ViewCartFakturAdapter()

    private var _binding: ActivityCekfakturplaceBinding? = null
    private val binding get() = _binding!!
    private val keranjang by extra<KeranjangTempat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCekfakturplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Detail Pesanan")

        setupExUI()
        setDataProduct()
        setUpAdapter()
        mainButton()
    }
    private fun mainButton(){
        binding.apply {
            btnKonfirmasi.setOnClickListener {
                konfirOrder()
            }
        }
    }

    private fun setUpAdapter(){
        binding.apply {
            rvData.adapter = adapter
        }
    }

    private fun setDataProduct(){
        val tempatId = Pref.getUser()?.tempat?.id
        viewModelProduct.getOrderByPlace(tempatId).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    binding.progressBar.toGone()
                    val data = it.data ?: emptyList()
                    adapter.addItems(data)
                }
                State.ERROR -> {
                    binding.progressBar.toGone()
                    toastError(it?.message ?: "Error")

                }
                State.LOADING -> {
                    binding.progressBar.toVisible()
                }
            }
        }
    }

    private fun setupExUI(){
        binding.apply {
            keranjang?.let {
                tvTotalqty.text = it.sum_qty.toString()
                tvTotalprice.text = it.sum_harga.toRupiah()
                tvStatus.text = it.status
            }
        }
    }


    private fun konfirOrder(){
        viewModel.updateConfirBuy(keranjang?.id).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    binding.progressBar.toGone()
                    konfirOrderToProduct()
                }
                State.ERROR -> {
                    binding.progressBar.toGone()
                    toastError(it?.message ?: "Error")

                }
                State.LOADING -> {
                    binding.progressBar.toVisible()
                }
            }
        }
    }

    private fun konfirOrderToProduct(){
        viewModelProduct.updateKonfirmasi(keranjang?.userId).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    binding.progressBar.toGone()
                }
                State.ERROR -> {
                    binding.progressBar.toGone()
                    toastError(it?.message ?: "Error")

                }
                State.LOADING -> {
                    binding.progressBar.toVisible()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}