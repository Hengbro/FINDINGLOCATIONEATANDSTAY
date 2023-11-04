package com.inyonghengki.findinglocation.core.data.source.remote.response

import com.inyonghengki.findinglocation.core.data.source.model.User

data class LoginResponse(
    val success: Int? =  null,
    val message:String? = null,
    val data: User?= null
)
