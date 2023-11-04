package com.inyonghengki.findinglocation.ui.keranjangproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.inyonghengki.findinglocation.core.data.repository.KeranjangProductRepository
import com.inyonghengki.findinglocation.core.data.source.model.KeranjangProduct

class KeranjangProductViewModel (private val repo: KeranjangProductRepository): ViewModel(){

    fun getCartByUser(userId: Int? = null) = repo.getCartByIdUser(userId).asLiveData()

    fun getOrderByPlace(tempatId: Int? = null) = repo.getCartByIdPlace(tempatId).asLiveData()

    fun getByOrder(tempatId: Int? = null) = repo.getByOrder(tempatId).asLiveData()

    fun getByCartPalceId(cartPlaceId: Int? = null) = repo.getByCartPlaceId(cartPlaceId).asLiveData()

    fun updateCartPlace(data: KeranjangProduct) = repo.updateCartPlace(data).asLiveData()

    fun addKeranjangProduct(data: KeranjangProduct) = repo.addKeranjangProduct(data).asLiveData()

    fun updateCreateOrder(userId :Int? = null) = repo.createOrder(userId).asLiveData()

    fun updateKonfirmasi(userId :Int? = null) = repo.konfirmasiOrder(userId).asLiveData()

    fun deleteProduk(id: Int?) = repo.deleteProduk(id).asLiveData()

}