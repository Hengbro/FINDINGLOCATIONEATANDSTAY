package com.inyonghengki.findinglocation.ui.feedback.history

import android.os.Bundle
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.ActivityListHistoryBinding
import com.inyonghengki.findinglocation.ui.base.MyActivity
import com.inyonghengki.findinglocation.ui.feedback.FeedbackViewModel
import com.inyonghengki.findinglocation.ui.feedback.adapter.ViewReallyBuyAdapter
import com.inyonghengki.findinglocation.util.Pref
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.setToolbar
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastError
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListHistoryTransUserActivity : MyActivity() {

    private val viewModel: FeedbackViewModel by viewModel()
    private val adapter = ViewReallyBuyAdapter(
        onClick = {
            intentActivity(TypesHistoryProductActivity::class.java,it)
        }
    )

    private var _binding: ActivityListHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.toolbar, "List Riwayat")

        setupAdepter()
        mainButton()
    }

    override fun onResume() {
        getViewStruk()
        super.onResume()
    }
    private fun mainButton(){

    }

    private fun setupAdepter(){
        binding.apply {
            rvStruk.adapter = adapter
        }
    }

    private fun getViewStruk(){
        val userId = Pref.getUser()?.id
        viewModel.getHistoryUser(userId).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    binding.pdTempatRekomend.toGone()

                    val data = it.data ?: emptyList()
                    adapter.addItems(data)
                }
                State.ERROR -> {
                    binding.pdTempatRekomend.toGone()
                    toastError(it?.message ?: "Error")

                }
                State.LOADING -> {
                    binding.pdTempatRekomend.toVisible()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

}