package com.inyonghengki.findinglocation.ui.slider

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.model.Slider
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityListDataBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.slider.adapter.SliderAdminAdapter
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.showConfirmDialog
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

 class ListSliderAdminActivity : MyActivity() {

     private lateinit var binding: ActivityListDataBinding
     private val viewModel: SliderAdminViewModel by viewModel()
     private var adapter = SliderAdminAdapter(
            onClick = {
                intentActivity(AddSliderAdminActivity::class.java, it)
            }, onDelete = { item, pos ->
                confirmDelete(item, pos)
            }
        )

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityListDataBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setToolbar(binding.toolbar, "List Slider")

            setupUI()
            mainButton()
            getData()
            setupAdapter()
        }

        override fun onResume() {
            getData()
            super.onResume()
        }

        private fun confirmDelete(item: Slider, pos: Int) {
            showConfirmDialog(
                "Delete Slider",
                "Apakah anda yakin ingin menghapus Slider ini?",
                "Delete"
            ) {
                onDelete(item, pos)
            }
        }

        private fun onDelete(item: Slider, pos: Int) {
            viewModel.delete(item.id).observe(this) {
                when (it.state) {
                    State.SUCCESS -> {
                        adapter.removeAt(pos)
                        progress.dismiss()
                        toastSuccess("Slider berhasil di hapus")
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

        private fun setupUI() {
            binding.apply {
                btnTambah.toVisible()
                btnTambah.setOnClickListener {
                    intentActivity(AddSliderAdminActivity::class.java)
                }
            }
        }

        private fun setupAdapter() {
            binding.rvData.adapter = adapter
        }

        private fun getData() {
            viewModel.get().observe(this) {
                when (it.state) {
                    State.SUCCESS -> {
                        //binding.swipeRefresh.dismissLoading()
                        binding.tvError.toGone()
                        val data = it.data ?: emptyList()
                        adapter.addItems(data)

                        if (data.isEmpty()) {
                            binding.tvError.toVisible()
                        }
                    }

                    State.ERROR -> {
                        binding.tvError.toVisible()
                    }

                    State.LOADING -> {

                    }
                }
            }
        }

        private fun mainButton() {
            binding.apply {
                btnTambah.toVisible()
                btnTambah.setOnClickListener {
                    intentActivity(AddSliderAdminActivity::class.java)
                }
            }
        }

        override fun onSupportNavigateUp(): Boolean {
            onBackPressedDispatcher.onBackPressed()
            return super.onSupportNavigateUp()
        }
    }