package com.inyonghengki.findinglocation.ui.menuproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.inyonghengki.findinglocation.core.data.repository.MenuProductRepository
import com.inyonghengki.findinglocation.core.data.source.model.MenuProduct
import okhttp3.MultipartBody

class MenuProductViewModel(private val repo: MenuProductRepository): ViewModel() {

    fun get() = repo.getMenuProduct().asLiveData()
    fun getProductDetail(id: Int? = null) = repo.getProductDetail(id).asLiveData()
    fun getProductDetailAll(id: Int? = null) = repo.getProductDetailAll(id).asLiveData()
    fun getFind(cari: String?)= repo.getFind(cari).asLiveData()
    fun getRelated(cari: String?) = repo.getRelated(cari).asLiveData()

    fun add(data: MenuProduct) = repo.addMenuProduct(data).asLiveData()

    fun update(data: MenuProduct) = repo.updateMenuProduct(data).asLiveData()

    fun delete(id: Int?) = repo.deleteMenuProduct(id).asLiveData()

    fun upload(fileImage: MultipartBody.Part? = null) = repo.uploadImage(fileImage).asLiveData()

}