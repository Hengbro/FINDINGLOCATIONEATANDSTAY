package com.inyonghengki.findinglocation.core.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import okhttp3.MultipartBody

class BaseViewModel(private val repo: AppRepository): ViewModel() {

    fun uploadImages(path: String, fileImage: MultipartBody.Part? = null) = repo.uploadImages(path, fileImage).asLiveData()

}