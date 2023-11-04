package com.inyonghengki.findinglocation.core.data.repository

import com.inyonghengki.findinglocation.core.data.source.local.LocalDataSource
import com.inyonghengki.findinglocation.core.data.source.remote.RemoteDataSource
import com.inyonghengki.findinglocation.core.data.source.remote.network.Resource
import com.inyonghengki.findinglocation.core.data.source.remote.request.SliderRequest
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.getErrorBody
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class SliderAdminRepository(val local: LocalDataSource, val remote: RemoteDataSource) {

    fun getSlider() = flow {
        emit(Resource.loading(null))
        try {
            remote.getSlider().let {
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

    fun addSlider(data: SliderRequest) = flow {
        emit(Resource.loading(null))
        try {
            remote.addSlider(data).let {
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

    fun updateSlider(data: SliderRequest) = flow {
        emit(Resource.loading(null))
        try {
            remote.updateSlider(data).let {
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

    fun deleteSlider(id: Int?) = flow {
        emit(Resource.loading(null))
        try {
            remote.deleteSlider(id).let {
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