package com.inyonghengki.findinglocation.ui.menuproduct

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.MenuProduct
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityListProductTempatBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.menuproduct.adapter.MenuProductAdapter
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastError
import com.inyongtisto.myhelper.extension.toastSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListMenuProductActivity : MyActivity() {

    private val viewModel : MenuProductViewModel by viewModel()
    private var adapter = MenuProductAdapter (
        onClick = {
            intentActivity(UpdateMenuProductActivity::class.java,it)
    },

        onDelete = { item, pos ->
            confirmDelete(item, pos)
        }
    )

    private var _binding: ActivityListProductTempatBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListProductTempatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "List Produk")

        mainButton()
        setupAdapter()

    }

    override fun onResume() {
        getData()
        super.onResume()
    }

    private fun confirmDelete(item: MenuProduct, pos: Int){
        viewModel.delete(item.id).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    adapter.removeAt(pos)
                    toastSuccess("Produk "+item.name+" berhasil di hapus")
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
        viewModel.get().observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    binding.progressBar.toGone()
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
                intentActivity(AddMenuProductActivity::class.java)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}