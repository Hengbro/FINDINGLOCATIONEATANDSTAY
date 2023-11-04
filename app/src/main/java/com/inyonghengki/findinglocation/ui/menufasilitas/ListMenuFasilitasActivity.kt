package com.inyonghengki.findinglocation.ui.menufasilitas

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.MenuFasilitas
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityListFasilitasTempatBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.menufasilitas.adapter.MenuFasilitasAdapter
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastError
import com.inyongtisto.myhelper.extension.toastSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListMenuFasilitasActivity : MyActivity() {

    private val viewModel : MenuFasilitasViewModel by viewModel()
    private var adapter = MenuFasilitasAdapter (
        onClick = {
            intentActivity(UpdateMenuFasilitasActivity::class.java,it)
    },

        onDelete = { item, pos ->
            confirmDelete(item, pos)
        }
    )

    private var _binding: ActivityListFasilitasTempatBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListFasilitasTempatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "List Fasilitas")

        mainButton()
        setupAdapter()

    }

    override fun onResume() {
        getData()
        super.onResume()
    }

    private fun confirmDelete(item: MenuFasilitas, pos: Int){
        viewModel.delete(item.id).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    adapter.removeAt(pos)
                    toastSuccess("Fasilitas "+item.name+" berhasil di hapus")
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
        viewModel.getFa().observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    binding.progressBar.toGone()
                    binding.tvError.toGone()
                    val data = it.data ?: emptyList()
                    adapter.addItems(data)

                    if(data.isEmpty()){
                        binding.tvError.toVisible()
                    }
                }
                State.ERROR -> {
                    binding.progressBar.toGone()
                    binding.tvError.toVisible()
                    toastError(it?.message ?: "Error")

                }
                State.LOADING -> {
                    binding.progressBar.toVisible()
                }
            }
        }
    }

    private fun mainButton(){

        binding.apply {
            btnAddproduct.setOnClickListener {
                intentActivity(AddMenuFasilitasActivity::class.java)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}