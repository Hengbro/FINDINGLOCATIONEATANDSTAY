package com.inyonghengki.findinglocation.core.data.source.remote.response

data class BaseListResponse<T>(
    val success: Int? =  null,
    val message: String? = null,
    val data: List<T> = emptyList()
)
