package com.inyonghengki.findinglocation.ui.searchplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.inyonghengki.findinglocation.core.data.repository.HistorySearchRepository
import com.inyonghengki.findinglocation.core.data.source.model.HistorySearch

class HistorySarchViewModel(private val repo: HistorySearchRepository): ViewModel() {

    fun get(userId: Int? = null) = repo.getHisSearch(userId).asLiveData()

    fun add(data: HistorySearch) = repo.addHisSearch(data).asLiveData()

    fun delete(id: Int?) = repo.deleteHisSeacrh(id).asLiveData()

}