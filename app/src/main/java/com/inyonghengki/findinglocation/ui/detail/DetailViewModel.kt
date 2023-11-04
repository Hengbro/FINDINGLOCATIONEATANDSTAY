package com.inyonghengki.findinglocation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.inyonghengki.findinglocation.core.data.repository.UlasanRepository
import com.inyonghengki.findinglocation.core.data.source.model.Pengunjung
import com.inyonghengki.findinglocation.core.data.source.model.Rating

class DetailViewModel(private val repo: UlasanRepository): ViewModel() {

    fun get() = repo.getUlasan().asLiveData()
    fun getRating(id: Int? = null) = repo.getRating(id).asLiveData()
    fun getRatingAll(id: Int? = null) = repo.getRatingAll(id).asLiveData()
    fun getReviewSolo(userId: Int? = null) = repo.getReviewSolo(userId).asLiveData()
    fun getReviewPotensi(tempatId: Int? = null) = repo.getReviewPotensi(tempatId).asLiveData()
    fun getReviewNoPotensi(tempatId: Int? = null) = repo.getReviewNoPotensi(tempatId).asLiveData()
    fun cekUlasan(data: Rating) = repo.cekUlasan(data).asLiveData()

    fun add(data: Rating) = repo.addUlasan(data).asLiveData()
    fun addPlaceAge(data: Pengunjung) = repo.addPlaceAge(data).asLiveData()

    fun updateJumlah(data: Rating) = repo.updateJumlah(data).asLiveData()
    fun updateTotal(data: Rating) = repo.updateTotal(data).asLiveData()

    fun update(data: Rating) = repo.updateUlasan(data).asLiveData()

}