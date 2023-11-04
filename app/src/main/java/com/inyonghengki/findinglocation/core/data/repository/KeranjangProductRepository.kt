package com.inyonghengki.findinglocation.core.data.repository

import com.inyonghengki.findinglocation.core.data.source.local.LocalDataSource
import com.inyonghengki.findinglocation.core.data.source.model.KeranjangProduct
import com.inyonghengki.findinglocation.core.data.source.remote.RemoteDataSource
import com.inyonghengki.findinglocation.core.data.source.remote.network.Resource
import com.inyongtisto.myhelper.extension.getErrorBody
import kotlinx.coroutines.flow.flow

class KeranjangProductRepository(val local: LocalDataSource, val remote: RemoteDataSource) {

    fun getCartByIdUser(userId: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getCartByIdUser(userId).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val data = body?.data

                    emit(Resource.success(data))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?:"Terjadi kesalahan", null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message?: "Terjadi masalah", null))
        }
    }

    fun getCartByIdPlace(tempatId: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getCartByIdPlace(tempatId).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val data = body?.data

                    emit(Resource.success(data))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?:"Terjadi kesalahan", null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message?: "Terjadi masalah", null))
        }
    }

    fun getByOrder(userId: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getByOrder(userId).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val data = body?.data

                    emit(Resource.success(data))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?:"Terjadi kesalahan", null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message?: "Terjadi masalah", null))
        }
    }

    fun addKeranjangProduct(data: KeranjangProduct) = flow {
        emit(Resource.loading(null))
        try {
            remote.addKeranjangProduct(data).let {
                if (it.isSuccessful){
                    val body = it.body()?.data
                    emit(Resource.success(body))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?:"Terjadi kesalahan", null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message?: "Terjadi masalah", null))
        }
    }

    fun createOrder(userId :Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.createOrder(userId).let {
                if (it.isSuccessful){
                    val body = it.body()?.data
                    emit(Resource.success(body))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?:"Terjadi kesalahan", null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message?: "Terjadi masalah", null))
        }
    }

    fun getByCartPlaceId(cartPlaceId: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getByCartPlaceId(cartPlaceId).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val data = body?.data

                    emit(Resource.success(data))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?:"Terjadi kesalahan", null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message?: "Terjadi masalah", null))
        }
    }

    fun updateCartPlace(data: KeranjangProduct) = flow {
        emit(Resource.loading(null))
        try {
            remote.updateCartPlace(data).let {
                if (it.isSuccessful){
                    val body = it.body()?.data
                    emit(Resource.success(body))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?:"Terjadi kesalahan", null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message?: "Terjadi masalah", null))
        }
    }

    fun konfirmasiOrder(userId :Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.konfirmasiOrder(userId).let {
                if (it.isSuccessful){
                    val body = it.body()?.data
                    emit(Resource.success(body))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?:"Terjadi kesalahan", null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message?: "Terjadi masalah", null))
        }
    }

    fun deleteProduk(id: Int?) = flow {
         emit(Resource.loading(null))
         try {
             remote.deleteProduk(id).let {
                 if (it.isSuccessful){
                     val body = it.body()
                     val data = body?.data
                     emit(Resource.success(data))
                 }else{
                     emit(Resource.error(it.getErrorBody()?.message?:"Terjadi kesalahan", null))
                 }
             }
         }catch (e:Exception){
             emit(Resource.error(e.message?: "Terjadi masalah", null))
         }
     }

    /*fun getProduk() = flow {
        Resource.loading(null)
        try {
            remote.getProduk().let {
                if (it.isSuccessful){
                    val body = it.body()
                    emit(Resource.success(body))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?:"Terjadi kesalahan", null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message?: "Terjadi masalah", null))
        }
    }*/
    /*class  ErrorCotume(
        val ok: Boolean,
        val error_code: Int,
        val description: String? = null
    )*/
}