package com.inyonghengki.findinglocation.core.data.source.remote.request

data class RegistrasiRequest(
    val name: String,
    val email: String,
    val phone: String,
    val kota: String,
    val tgllahir: String,
    val password: String,
)