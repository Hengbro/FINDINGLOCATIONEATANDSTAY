package com.inyonghengki.findinglocation.ui.keranjangproduct

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.KeranjangProduct
import com.inyonghengki.findinglocation.core.data.source.model.KeranjangTempat
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityCartproductBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.feedback.FeedbackViewModel
import com.inyonghengki.findinglocation.ui.keranjangproduct.adapter.ViewKeranjangPesananAdapter
import com.inyonghengki.findinglocation.util.Pref
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showConfirmDialog
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastError
import com.inyongtisto.myhelper.extension.toastSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

class KeranjangProductActivity : MyActivity() {

    private val viewModel : KeranjangProductViewModel by viewModel()
    private val viewModelTempat : FeedbackViewModel by viewModel()
    private val adapter = ViewKeranjangPesananAdapter(
        onDelete = { item, pos ->
            confirmDelete(item, pos)
        }
    )

    private var _binding: ActivityCartproductBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCartproductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "Keranjang")


        mainButton()
        setupAdepter()

    }

    override fun onResume() {
        setData()
        super.onResume()
    }

    private fun setupAdepter(){
        binding.apply {
            rvTempatRekomend.adapter = adapter
        }
    }

    private fun mainButton(){
        binding.apply {
            btnOrder.setOnClickListener {
                addOrder()
            }
        }
    }

    private fun confirmDelete(item: KeranjangProduct, pos: Int) {
        showConfirmDialog(
            "Delete Produk Pesanan",
            "Apakah anda yakin ingin menghapus pesanan ini?",
            "Delete"
        ) {
            onDelete(item, pos)
        }
    }

    private fun onDelete(item: KeranjangProduct, pos: Int) {
        viewModel.deleteProduk(item.id).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    adapter.removeAt(pos)
                    progress.dismiss()
                    toastSuccess("Produk berhasil di hapus")
                }

                State.ERROR -> {
                    showErrorDialog(it.message.defaultError())
                    progress.dismiss()
                }

                State.LOADING -> {
                    progress.show()
                }
            }
        }
    }

    private fun setData(){
        val userId = Pref.getUser()?.id
        viewModel.getCartByUser(userId).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    binding.pdTempatRekomend.toGone()
                    val data = it.data ?: emptyList()
                    adapter.addItems(data)
                    binding.btnOrder.toVisible()
                }
                State.ERROR -> {
                    binding.pdTempatRekomend.toGone()
                    binding.btnOrder.toGone()
                    toastError(it?.message ?: "Error")

                }
                State.LOADING -> {
                    binding.pdTempatRekomend.toVisible()
                }
            }
        }
    }

    private fun addOrder(){
        val userId = Pref.getUser()?.id
        viewModel.updateCreateOrder(userId).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    binding.pdOrder.toGone()
                    onBackPressedDispatcher.onBackPressed()
                    toastSuccess(it.message?:"Tunggu pesanan-mu datang")
                    addKeranjangTempat()
                }
                State.ERROR -> {
                    binding.pdOrder.toGone()
                    toastError(it?.message ?: "Error")

                }
                State.LOADING -> {
                    binding.pdOrder.toVisible()
                }
            }
        }
    }

    private fun addKeranjangTempat(){
        val id = intent.getIntExtra("id", 0)
        val reqData = KeranjangTempat(
            userId = Pref.getUser()?.id,
            tempatId = id
        )
        viewModelTempat.addKeranjangTempat(reqData).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    updateCartPlace()
                }
                State.ERROR -> {
                    toastError(it?.message ?: "Error")
                }
                State.LOADING -> {

                }
            }
        }
    }

    private fun updateCartPlace(){
        val id = intent.getIntExtra("id", 0)
        val reqData = KeranjangProduct (
            userId = Pref.getUser()?.id,
            tempatId = id
        )
        viewModel.updateCartPlace(reqData).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    binding.pdOrder.toGone()
                    onBackPressedDispatcher.onBackPressed()
                    toastSuccess(it.message?:"Tunggu pesanan-mu datang")
                    addKeranjangTempat()
                }
                State.ERROR -> {
                    binding.pdOrder.toGone()
                    toastError(it?.message ?: "Error")

                }
                State.LOADING -> {
                    binding.pdOrder.toVisible()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}