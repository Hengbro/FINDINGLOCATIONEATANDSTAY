package com.inyonghengki.findinglocation.core.data.repository

import com.inyonghengki.findinglocation.core.data.source.local.LocalDataSource
import com.inyonghengki.findinglocation.core.data.source.model.KeranjangTempat
import com.inyonghengki.findinglocation.core.data.source.remote.RemoteDataSource
import com.inyonghengki.findinglocation.core.data.source.remote.network.Resource
import com.inyongtisto.myhelper.extension.getErrorBody
import kotlinx.coroutines.flow.flow

class KeranjangTempatRepository(val local: LocalDataSource, val remote: RemoteDataSource) {

    fun getBuyByUser(userId: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getBuyByUser(userId).let {
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

    fun getBuyByPlace(tempatId: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getBuyByPlace(tempatId).let {
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

    fun getHistoryUser(userId: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getHistoryUser(userId).let {
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

    fun getHistoryPlace(tempatId: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getHistoryPlace(tempatId).let {
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

    fun addKeranjangTempat(data: KeranjangTempat) = flow {
        emit(Resource.loading(null))
        try {
            remote.addKeranjangTempat(data).let {
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

    fun confirmationBuy(id: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.confirmationBuy(id).let {
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

    /*fun deletetLokasiTempat(id: Int?) = flow {
         emit(Resource.loading(null))
         try {
             remote.deleteLokasiTempat(id).let {
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

     fun getProduk() = flow {
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