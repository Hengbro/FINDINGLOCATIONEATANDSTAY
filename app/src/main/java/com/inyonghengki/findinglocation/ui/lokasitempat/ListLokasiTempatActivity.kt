package com.inyonghengki.findinglocation.ui.lokasitempat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.LokasiTempat
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityListLokasiTempatBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.lokasitempat.adapter.ListLokasiAdapter
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showConfirmDialog
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toJson
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListLokasiTempatActivity : MyActivity() {

    private val viewModel : LokasiTempatViewModel by viewModel()
    private val adapter = ListLokasiAdapter (
        onClick = {
            val intent = Intent()
            intent.putExtra("extra", it.toJson())
            setResult(Activity.RESULT_OK, intent)
            onBackPressedDispatcher.onBackPressed()
        }, onDelete = { item, pos ->
            confirmDelete(item, pos)
        }
    )

        private var _binding: ActivityListLokasiTempatBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListLokasiTempatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "List Lokasi")

        mainButton()
        setupAdapter()

    }

    private fun confirmDelete(item: LokasiTempat, pos: Int) {
        showConfirmDialog(
            "Delete Lokasi",
            "Apakah anda yakin ingin menghapus Category ini?",
            "Delete"
        ) {
            onDelete(item, pos)
        }
    }

    override fun onResume() {
        getData()
        super.onResume()
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

                }
                State.LOADING -> {
                    binding.progressBar.toVisible()

                }
            }
        }
    }

    private fun onDelete(item: LokasiTempat,pos: Int){
        viewModel.delete(item.id).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    adapter.removeAt(pos)
                    toastSuccess("Lokasi "+item.alamat+" berhasil di hapus")
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

    private fun mainButton(){

        binding.apply {
            btnTambahAlamat.setOnClickListener {
                intentActivity(AddLokasiActivity::class.java)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}