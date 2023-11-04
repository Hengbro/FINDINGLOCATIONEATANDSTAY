package com.inyonghengki.findinglocation.ui.searchplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.core.view.isEmpty
import com.inyonghengki.findinglocation.core.data.source.model.HistorySearch
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivitySearchPlaceBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.home.HomeViewModel
import com.inyonghengki.findinglocation.ui.searchplace.adabter.CategoryAdapter
import com.inyonghengki.findinglocation.ui.searchplace.adabter.HistorySearchAdapter
import com.inyonghengki.findinglocation.util.Pref
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("NotifyDataSetChanged")
class SearchActivity : MyActivity() {

    private lateinit var  binding: ActivitySearchPlaceBinding
    private val viewModelHome: HomeViewModel by viewModel()
    private val viewModelHisSearch: HistorySarchViewModel by viewModel()
    private val adapterCategory = CategoryAdapter()
    private val adapterHisSearch = HistorySearchAdapter(
        onDelete = { item, pos ->
            onDelete(item, pos)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainButton()
        setupAdapter()
        setDataCategory()
    }

    private fun setupAdapter() {
        binding.apply {
            rvCategory.adapter = adapterCategory
            rvHistorysearch.adapter = adapterHisSearch
        }
    }

    override fun onResume() {
        setDataHisSearch()
        super.onResume()
    }

    private fun setDataHisSearch() {
        val userId = Pref.getUser()?.id?:0
        viewModelHisSearch.get(userId).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    val data = it.data ?: emptyList()
                    adapterHisSearch.addItems(data)
                }
                State.ERROR -> {
                }
                State.LOADING -> {
                }
            }
        }
    }

    private fun setDataCategory() {
        viewModelHome.getCate().observe(this) {
            when (it.state) {
                State.SUCCESS -> {

                    binding.pdTempatRekomend.toGone()
                    val data = it.data ?: emptyList()
                    adapterCategory.addItems(data)
                }

                State.ERROR -> {
                    binding.pdTempatRekomend.toGone()
                }

                State.LOADING -> {

                    binding.pdTempatRekomend.toVisible()

                }
            }
        }
    }

    private fun mainButton(){
        binding.apply {
            btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (validate())

                        cari(query)
                        Tambah(query)

                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    // Tidak ada tindakan yang diambil saat teks berubah secara real-time
                    return false
                }
            })
        }
    }

    private fun Tambah(query: String) {

        val reqData = HistorySearch(
            name = query,
            userId = Pref.getUser()?.id?:0,
        )

        viewModelHisSearch.add(reqData).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                }
                State.ERROR -> {
                }
                State.LOADING -> {
                }
            }
        }
    }

    private fun onDelete(item: HistorySearch, pos: Int) {
        viewModelHisSearch.delete(item.id).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    adapterHisSearch.removeAt(pos)
                    progress.dismiss()
                    toastSuccess("Data berhasil di hapus")
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
    private fun validate(): Boolean{
        binding.apply {
            if (searchView.isEmpty())return true
        }
        return true
    }

    private fun cari(query: String){
        val intent = Intent(this, ResultHasilActivity::class.java)
        intent.apply {
            putExtra("themessage", query)
        }
        startActivity(intent)
    }

}
