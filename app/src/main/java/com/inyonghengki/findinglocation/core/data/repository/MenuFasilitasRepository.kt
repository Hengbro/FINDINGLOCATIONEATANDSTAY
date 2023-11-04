package com.inyonghengki.findinglocation.core.data.repository

import com.inyonghengki.findinglocation.core.data.source.local.LocalDataSource
import com.inyonghengki.findinglocation.core.data.source.model.MenuFasilitas
import com.inyonghengki.findinglocation.core.data.source.remote.RemoteDataSource
import com.inyonghengki.findinglocation.core.data.source.remote.network.Resource
import com.inyongtisto.myhelper.extension.getErrorBody
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody

class MenuFasilitasRepository(val local: LocalDataSource, val remote: RemoteDataSource) {

    fun getMenuFasilitasAll() = flow {
        emit(Resource.loading(null))
        try {
            remote.getMenuFasilitasAll().let {
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

    fun getMenuFasilitas() = flow {
        emit(Resource.loading(null))
        try {
            remote.getMenuFasilitas().let {
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

    fun getFasilitasDetail(id: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getFasilitasDetail(id).let {
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

    fun getFasilitasDetailAll(id: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getFasilitasDetailAll(id).let {
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

    fun addMenuFasilitas(data: MenuFasilitas) = flow {
        emit(Resource.loading(null))
        try {
            remote.addMenuFasilitas(data).let {
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

    fun updateMenuFasilitas(data: MenuFasilitas) = flow {
        emit(Resource.loading(null))
        try {
            remote.updateMenuFasilitas(data).let {
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

    fun deleteMenuFasilitas(id: Int?) = flow {
        emit(Resource.loading(null))
        try {
            remote.deleteMenuFasilitas(id).let {
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

    fun uploadImageFa(fileImage: MultipartBody.Part? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.uploadImageFa(fileImage).let {
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