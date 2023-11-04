package com.inyonghengki.findinglocation.ui.tempat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.inyonghengki.findinglocation.core.data.repository.AppRepository
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import okhttp3.MultipartBody

class TempatViewModel(val repo: AppRepository): ViewModel() {

    fun get(id: Int? = null) = repo.getTempat(id).asLiveData()

    fun store(data: Tempat) = repo.store(data).asLiveData()

    fun update(data: Tempat) = repo.updateTempat(data).asLiveData()

    fun delete(id: Int?) = repo.deleteTempat(id).asLiveData()

    fun viewConfrimation() = repo.viewConfirmation().asLiveData()

    fun updateConfirmation(id: Int?, data: Tempat) = repo.updateConfirmation(id, data).asLiveData()

    fun deletePlace(id: Int?) = repo.deletePlace(id).asLiveData()

    fun getOne(id: Int?) = repo.getOne(id).asLiveData()

    fun getView(id: Int?) = repo.getView(id).asLiveData()

    fun uploadTe(fileImage: MultipartBody.Part? = null) = repo.uploadImageTempat(fileImage).asLiveData()

    fun uploadPe(fileImage: MultipartBody.Part? = null) = repo.uploadImagePemilik(fileImage).asLiveData()

}