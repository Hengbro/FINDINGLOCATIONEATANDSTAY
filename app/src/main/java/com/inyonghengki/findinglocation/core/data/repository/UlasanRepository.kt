package com.inyonghengki.findinglocation.core.data.repository

import com.inyonghengki.findinglocation.core.data.source.local.LocalDataSource
import com.inyonghengki.findinglocation.core.data.source.model.Pengunjung
import com.inyonghengki.findinglocation.core.data.source.model.Rating
import com.inyonghengki.findinglocation.core.data.source.remote.RemoteDataSource
import com.inyonghengki.findinglocation.core.data.source.remote.network.Resource
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.getErrorBody
import kotlinx.coroutines.flow.flow

class UlasanRepository(val local: LocalDataSource, val remote: RemoteDataSource) {

    fun getUlasan() = flow {
        emit(Resource.loading(null))
        try {
            remote.getUlasan().let {
                if (it.isSuccessful){
                    val body = it.body()
                    val data = body?.data
                    emit(Resource.success(data))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message.defaultError(), null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message.defaultError(), null))
        }
    }

    fun getRating(id: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getRating(id).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val data = body?.data
                    emit(Resource.success(data))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message.defaultError(), null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message.defaultError(), null))
        }
    }

    fun getRatingAll(id: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getRatingAll(id).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val data = body?.data
                    emit(Resource.success(data))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message.defaultError(), null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message.defaultError(), null))
        }
    }

    fun getReviewSolo(userId: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getReviewSolo(userId).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val data = body?.data
                    emit(Resource.success(data))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message.defaultError(), null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message.defaultError(), null))
        }
    }

    fun getReviewPotensi(tempatId: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getReviewPotensi(tempatId).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val data = body?.data
                    emit(Resource.success(data))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message.defaultError(), null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message.defaultError(), null))
        }
    }

    fun getReviewNoPotensi(tempatId: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getReviewNoPotensi(tempatId).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val data = body?.data
                    emit(Resource.success(data))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message.defaultError(), null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message.defaultError(), null))
        }
    }

    fun cekUlasan(data: Rating) = flow {
        emit(Resource.loading(null))
        try {
            remote.cekUlasan(data).let {
                if (it.isSuccessful){
                    val body = it.body()?.data
                    emit(Resource.success(body))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message.defaultError(), null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message.defaultError(), null))
        }
    }


    fun addUlasan(data: Rating) = flow {
        emit(Resource.loading(null))
        try {
            remote.addUlasan(data).let {
                if (it.isSuccessful){
                    val body = it.body()?.data
                    emit(Resource.success(body))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message.defaultError(), null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message.defaultError(), null))
        }
    }

    fun addPlaceAge(data: Pengunjung) = flow {
        emit(Resource.loading(null))
        try {
            remote.addPlaceAge(data).let {
                if (it.isSuccessful){
                    val body = it.body()?.data
                    emit(Resource.success(body))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message.defaultError(), null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message.defaultError(), null))
        }
    }

    fun updateJumlah(data: Rating) = flow {
        emit(Resource.loading(null))
        try {
            remote.updateJumlah(data).let {
                if (it.isSuccessful){
                    val body = it.body()?.data
                    emit(Resource.success(body))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message.defaultError(), null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message.defaultError(), null))
        }
    }

    fun updateUlasan(data: Rating) = flow {
        emit(Resource.loading(null))
        try {
            remote.updateUlasan(data).let {
                if (it.isSuccessful){
                    val body = it.body()?.data
                    emit(Resource.success(body))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message.defaultError(), null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message.defaultError(), null))
        }
    }

    fun updateTotal(data: Rating) = flow {
        emit(Resource.loading(null))
        try {
            remote.updateTotal(data).let {
                if (it.isSuccessful){
                    val body = it.body()?.data
                    emit(Resource.success(body))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message.defaultError(), null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message.defaultError(), null))
        }
    }

    /*fun uploadImageCategoryA(fileImage: MultipartBody.Part? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.uploadImageCategoryA(fileImage).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val fileName = body?.data

                    emit(Resource.success(fileName))
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