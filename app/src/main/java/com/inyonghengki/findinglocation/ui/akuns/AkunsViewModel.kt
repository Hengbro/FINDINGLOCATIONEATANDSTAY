package com.inyonghengki.findinglocation.ui.akuns

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.inyonghengki.findinglocation.core.data.repository.AppRepository
import com.inyonghengki.findinglocation.core.data.repository.MenuFasilitasRepository

class AkunsViewModel (private val repo: AppRepository): ViewModel() {

    fun getHome() = repo.getHome().asLiveData()
}