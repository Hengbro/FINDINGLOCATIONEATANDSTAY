package com.inyonghengki.findinglocation.ui.categoryproduct

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.CategoryProduct
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityListCategoryAdminBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.categoryproduct.adapter.CategoryProductAdminAdapter
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showConfirmDialog
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastError
import com.inyongtisto.myhelper.extension.toastSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListCategoryProductAdminActivity : MyActivity() {

    private val viewModel : CategotryProductAdminViewModel by viewModel()
    private var adapter = CategoryProductAdminAdapter (
        onClick = {
            intentActivity(AddCategoryProductAdminActivity::class.java,it)
    },

        onDelete = { item, pos ->
            confirmDelete(item, pos)
        }
    )

    private var _binding: ActivityListCategoryAdminBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListCategoryAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "List Kategori Product")

        mainButton()
        setupAdapter()
        getData()

    }

    override fun onResume() {
        getData()
        super.onResume()
    }

    private fun confirmDelete(item: CategoryProduct, pos: Int){
        showConfirmDialog(
            "Hapus Kategori",
            "Apakah anda yakin ingin menghapus Category ini ?",
            "Hapus"
        ){
            onDelete(item, pos)
        }
    }

    private fun onDelete(item: CategoryProduct, pos: Int){
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
            btnAddcategory.setOnClickListener {
                intentActivity(AddCategoryProductAdminActivity::class.java)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}