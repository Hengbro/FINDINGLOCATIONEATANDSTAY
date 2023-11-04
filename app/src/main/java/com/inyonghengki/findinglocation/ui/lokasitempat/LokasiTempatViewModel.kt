package com.inyonghengki.findinglocation.ui.lokasitempat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.inyonghengki.findinglocation.core.data.repository.LokasiRepository
import com.inyonghengki.findinglocation.core.data.source.model.LokasiTempat
import com.inyonghengki.findinglocation.core.data.source.remote.request.TempatRequest

class LokasiTempatViewModel(private val repo: LokasiRepository): ViewModel() {

    fun get() = repo.getLokasiTempat().asLiveData()

    fun add(data: LokasiTempat) = repo.addLokasiTempat(data).asLiveData()

    fun update(data: LokasiTempat) = repo.updateLokasiTempat(data).asLiveData()

    fun delete(id: Int?) = repo.deletetLokasiTempat(id).asLiveData()

}