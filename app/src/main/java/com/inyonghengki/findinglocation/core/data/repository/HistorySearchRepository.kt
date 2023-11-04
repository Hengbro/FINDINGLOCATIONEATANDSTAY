package com.inyonghengki.findinglocation.core.data.repository

import com.inyonghengki.findinglocation.core.data.source.local.LocalDataSource
import com.inyonghengki.findinglocation.core.data.source.model.HistorySearch
import com.inyonghengki.findinglocation.core.data.source.remote.RemoteDataSource
import com.inyonghengki.findinglocation.core.data.source.remote.network.Resource
import com.inyonghengki.findinglocation.util.defaultError
import com.inyongtisto.myhelper.extension.getErrorBody
import kotlinx.coroutines.flow.flow

class HistorySearchRepository(val local: LocalDataSource, val remote: RemoteDataSource) {

    fun getHisSearch(userId: Int? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.getHisSearch(userId).let {
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

    fun addHisSearch(data: HistorySearch) = flow {
        emit(Resource.loading(null))
        try {
            remote.addHisSearch(data).let {
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

    fun deleteHisSeacrh(id: Int?) = flow {
        emit(Resource.loading(null))
        try {
            remote.delHisSearch(id).let {
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
}