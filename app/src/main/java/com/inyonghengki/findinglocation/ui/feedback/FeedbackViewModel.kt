package com.inyonghengki.findinglocation.ui.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.inyonghengki.findinglocation.core.data.repository.KeranjangTempatRepository
import com.inyonghengki.findinglocation.core.data.source.model.KeranjangTempat

class FeedbackViewModel (private val repo: KeranjangTempatRepository): ViewModel() {

    fun getBuyByUser(userId: Int? = null) = repo.getBuyByUser(userId).asLiveData()

    fun getBuyByPlace(tempatId: Int? = null) = repo.getBuyByPlace(tempatId).asLiveData()

    fun getHistoryUser(userId: Int? = null) = repo.getHistoryUser(userId).asLiveData()

    fun getHistoryPlace(tempatId: Int? = null) = repo.getHistoryPlace(tempatId).asLiveData()

    fun addKeranjangTempat(data: KeranjangTempat) = repo.addKeranjangTempat(data).asLiveData()

    fun updateConfirBuy(id: Int? = null) = repo.confirmationBuy(id).asLiveData()

    //fun delete(id: Int?) = repo.deleteFavorite(id).asLiveData()
}