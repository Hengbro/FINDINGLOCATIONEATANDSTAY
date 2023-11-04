package com.inyonghengki.findinglocation.ui.adminpanel.tempat

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityListDataConfirmationBinding
import com.inyonghengki.findinglocation.ui.adminpanel.tempat.adapter.MenuConfirmationAdapter
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.tempat.TempatViewModel
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.extra
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.showToast
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toastError
import com.inyongtisto.myhelper.extension.toastSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListPlaceForConfirmation : MyActivity() {

    private val viewModel : TempatViewModel by viewModel()
    private val tempat by extra<Tempat>()
    private var adapter = MenuConfirmationAdapter (
        onClick = {
            update(it)
        },

        onDelete = { item, pos ->
            confirmDelete(item, pos)
        },
        onClicktwo = {
            detailTempat(it)
        }
    )

    private var _binding: ActivityListDataConfirmationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListDataConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "List Tempat")

        mainButton()
        setupAdapter()

    }

    override fun onResume() {
        getData()
        super.onResume()
    }

    private fun confirmDelete(item: Tempat, pos: Int){
        viewModel.deletePlace(item.id).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    adapter.removeAt(pos)
                    toastSuccess("Tempat "+item.nameTempat+" berhasil di hapus")
                }
                State.ERROR -> {
                    showErrorDialog(it?.message.defaultError())
                    progress.dismiss()
                }
                State.LOADING -> {
                    progress.show()
                }
            }
        }
    }

    private fun setupAdapter(){
        binding.rvData.adapter = adapter
    }

    private fun getData(){
        viewModel.viewConfrimation().observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    binding.tvError.toGone()

                    val data = it.data ?: emptyList()
                    adapter.addItems(data)
                }

                State.ERROR -> {
                    binding.tvError.toGone()
                    toastError(it?.message ?: "Error")

                }

                State.LOADING -> {

                }
            }
        }
    }

    private fun update(item: Tempat) {

        val reqData = Tempat(
            isActive = true,
            status = "Accept"
        )

        viewModel.updateConfirmation(item.id, reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    showToast("Berhasil mengkonfirmasi data")
                }
                State.ERROR -> {
                    progress.dismiss()
                    showErrorDialog(it.message.defaultError())
                }
                State.LOADING -> {
                    progress.show()
                }
            }
        }
    }

    private fun detailTempat(tempat: Tempat) {

        viewModel.getView(tempat.id).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    intentActivity(CekTempatActivity::class.java, it.data)
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

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}