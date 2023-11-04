package com.inyonghengki.findinglocation.core.data.source.remote.response

data class BaseSingleResponse<T>(
    val success: Int? =  null,
    val message:String? = null,
    val data: T?= null
)
