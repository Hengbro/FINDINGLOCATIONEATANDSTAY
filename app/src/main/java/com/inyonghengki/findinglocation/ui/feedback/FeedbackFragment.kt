package com.inyonghengki.findinglocation.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inyonghengki.findinglocation.core.data.source.remote.network.State
import com.inyonghengki.findinglocation.databinding.FragmentFeedbackBinding
import com.inyonghengki.findinglocation.ui.base.MyFragment
import com.inyonghengki.findinglocation.ui.feedback.adapter.ViewReallyBuyAdapter
import com.inyonghengki.findinglocation.ui.feedback.history.ListHistoryTransUserActivity
import com.inyonghengki.findinglocation.ui.searchplace.SearchActivity
import com.inyonghengki.findinglocation.util.Pref
import com.inyongtisto.myhelper.extension.intentActivity
import com.inyongtisto.myhelper.extension.popUpMenu
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible
import com.inyongtisto.myhelper.extension.toastError
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedbackFragment : MyFragment() {

    private var _binding: FragmentFeedbackBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FeedbackViewModel by viewModel()
    private val adapter = ViewReallyBuyAdapter(
        onClick = {
            intentActivity(TypesProductActivity::class.java,it)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFeedbackBinding.inflate(inflater, container, false)
        val root: View = binding.root


        setupAdepter()
        mainButton()
        return root
    }

    override fun onResume() {
        getViewStruk()
        super.onResume()
    }

    private fun setupAdepter(){
        binding.apply {
            rvStruk.adapter = adapter
        }
    }

    private fun mainButton(){
        binding.apply {
            val context = root.context
            lySearch.setOnClickListener {
                intentActivity(SearchActivity::class.java)
            }
            btnMenu.setOnClickListener {
                val listMenu = listOf("Riwayat")
                context.popUpMenu(btnMenu, listMenu){
                    when(it){
                        "Riwayat" -> intentActivity(ListHistoryTransUserActivity::class.java)
                    }
                }
            }
        }
    }

    private fun getViewStruk(){
        val userId = Pref.getUser()?.id
        viewModel.getBuyByUser(userId).observe(requireActivity()){
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}