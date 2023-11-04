package com.inyonghengki.findinglocation.ui.tempat

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.KeranjangTempat
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityKonfirmationBuyBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.feedback.FeedbackViewModel
import com.inyonghengki.findinglocation.ui.keranjangproduct.KeranjangProductViewModel
import com.inyonghengki.findinglocation.ui.tempat.adapter.ViewUserOrderAdapter
import com.inyonghengki.findinglocation.util.Pref
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.popUpMenu
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastError
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConfirmationBuyActivity : MyActivity() {

    private val viewModel : FeedbackViewModel by viewModel()
    private val viewModelCartProduct: KeranjangProductViewModel by viewModel()

    private val adapter = ViewUserOrderAdapter(
        onClick={item, pos ->
            konfirOrder(item, pos)
        }, onTwoClick={
            intentActivity(TypesOrderByPlaceActivity::class.java,it)
        }
    )
    val user = Pref.getUser()

    private var _binding: ActivityKonfirmationBuyBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityKonfirmationBuyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Konfirmasi Bayar")

        setupAdepter()
        mainButton()
    }

    override fun onResume() {
        setData()
        super.onResume()
    }

    private fun setupAdepter(){
        binding.apply {
            rvData.adapter = adapter
        }
    }

    private fun mainButton(){
        binding.apply {
            val context = root.context
            btnMenu.setOnClickListener {
                val listMenu = listOf("Riwayat")
                context.popUpMenu(btnMenu, listMenu){
                    when(it){
                        "Riwayat" -> intentActivity(ListHistoryTransPlaceActivity::class.java)
                    }
                }
            }
        }
    }

    private fun setData(){
        val tempatId = Pref.getUser()?.tempat?.id
        viewModel.getBuyByPlace(tempatId).observe(this){
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

    private fun konfirOrder(item: KeranjangTempat, pos: Int){
        viewModel.updateConfirBuy(item.id).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    binding.progressBar.toGone()
                    adapter.removeAt(pos)
                    konfirOrderToProduct(item)
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

    private fun konfirOrderToProduct(item: KeranjangTempat){
        viewModelCartProduct.updateKonfirmasi(item.userId).observe(this){
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