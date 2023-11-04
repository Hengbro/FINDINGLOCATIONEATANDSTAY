package com.inyonghengki.findinglocation.core.data.repository

import com.inyonghengki.findinglocation.core.data.source.local.LocalDataSource
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import com.inyonghengki.findinglocation.core.data.source.remote.RemoteDataSource
import com.inyonghengki.findinglocation.core.data.source.remote.network.Resource
import com.inyonghengki.findinglocation.core.data.source.remote.request.LoginRequest
import com.inyonghengki.findinglocation.core.data.source.remote.request.RegistrasiRequest
import com.inyonghengki.findinglocation.core.data.source.remote.request.UpdateProfileRequest
import com.inyonghengki.findinglocation.util.Pref
import com.inyongtisto.myhelper.extension.getErrorBody
import com.inyongtisto.myhelper.extension.logs
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody

class AppRepository(val local: LocalDataSource, val remote: RemoteDataSource) {

    fun login(data: LoginRequest) = flow {
        emit(Resource.loading(null))
        try {
            remote.login(data).let {
                if (it.isSuccessful){
                    Pref.isLogin = true
                    val body = it.body()
                    val user = body?.data
                    Pref.setUser(user)
                    Pref.token = user?.token ?: "tokenError"
                    emit(Resource.success(user))
                    logs("success: "+ body.toString())
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?: "Terjadi kesalahan", null))
                    logs("Error:" + "Keteangan error")
                }
            }
        }catch (e: Exception){
            emit(Resource.error(e.message?:"Terjadi kesalahan", null))
            logs("Error:" + e.message)
        }
    }


    fun register(data: RegistrasiRequest) = flow {
        emit(Resource.loading(null))
        try {
            remote.register(data).let {
                if (it.isSuccessful){
                    //Prefs.isLogin = true
                    val body = it.body()
                    val user = body?.data
                    //Prefs.setUser(user)
                    emit(Resource.success(user))
                    logs("success: "+ body.toString())
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?:"Terjadi kesalahan", null))
                    logs("Error:"+ "Keterangan error")
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message?: "Terjadi masalah", null))
            logs("Error:"+ e.message)
        }
    }

    fun updateProfil(data: UpdateProfileRequest) = flow {
        emit(Resource.loading(null))
        try {
            remote.updateProfil(data).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    Pref.setUser(user)
                    emit(Resource.success(user))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?:"Terjadi kesalahan", null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message?: "Terjadi masalah", null))
        }
    }

    fun uploadImgUser(id: Int? = null, fileImage: MultipartBody.Part? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.uploadImgUser(id, fileImage).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    Pref.setUser(user)
                    emit(Resource.success(user))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?:"Terjadi kesalahan", null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message?: "Terjadi masalah", null))
        }
    }

    fun getTempat(id: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getTempat(id).let {
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

    fun store(data: Tempat) = flow {
        emit(Resource.loading(null))
        try {
            remote.
            store(data).let {
                if (it.isSuccessful){
                    //Prefs.isLogin = true
                    val body = it.body()?.data
                    //Prefs.setUser(user)
                    emit(Resource.success(body))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?:"Terjadi kesalahan", null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message?: "Terjadi masalah", null))
            logs("Error:"+ e.message)
        }
    }

    fun updateTempat(data: Tempat) = flow {
        emit(Resource.loading(null))
        try {
            remote.updateTempat(data).let {
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

    fun deleteTempat(id: Int?) = flow {
        emit(Resource.loading(null))
        try {
            remote.deleteTempat(id).let {
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

    fun viewConfirmation() = flow {
        emit(Resource.loading(null))
        try {
            remote.viewConfirmation().let {
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

    fun updateConfirmation(id: Int?, data: Tempat) = flow {
        emit(Resource.loading(null))
        try {
            remote.updateConfirmation(id, data).let {
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

    fun deletePlace(id: Int?) = flow {
        emit(Resource.loading(null))
        try {
            remote.deletePlace(id).let {
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

    fun getOne(id: Int?) = flow {
        emit(Resource.loading(null))
        try {
            remote.getOne(id).let {
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

    fun getView(id: Int?) = flow {
        emit(Resource.loading(null))
        try {
            remote.getView(id).let {
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

    fun uploadImageTempat(fileImage: MultipartBody.Part? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.uploadImageTempat(fileImage).let {
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

    fun uploadImagePemilik(fileImage: MultipartBody.Part? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.uploadImagePemilik(fileImage).let {
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

    fun cekTempat(id: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.cekTempat(id).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data
                    Pref.setUser(user)
                    emit(Resource.success(user))
                }else{
                    emit(Resource.error(it.getErrorBody()?.message?:"Terjadi kesalahan", null))
                }
            }
        }catch (e:Exception){
            emit(Resource.error(e.message?: "Terjadi masalah", null))
        }
    }

    fun getHome() = flow {
        emit(Resource.loading(null))
        try {
            remote.getHome().let {
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

    fun getNew() = flow {
        emit(Resource.loading(null))
        try {
            remote.getNew().let {
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

    fun getSlider() = flow {
        emit(Resource.loading(null))
        try {
            remote.getHomeSlider().let {
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

    fun getPlaceAge(userId: Int?  = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getPlaceAge(userId).let {
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

    fun getPlaceByFas(id: Int?  = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getPlaceByFas(id).let {
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

    fun getCate() = flow {
        emit(Resource.loading(null))
        try {
            remote.getCate().let {
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

    fun uploadImages(path: String, fileImage: MultipartBody.Part? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.uploadImages(path, fileImage).let {
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

}