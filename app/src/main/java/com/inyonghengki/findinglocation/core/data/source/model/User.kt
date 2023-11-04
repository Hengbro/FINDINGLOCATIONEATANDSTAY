package com.inyonghengki.findinglocation.core.data.source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val created_at: String?,
    val tgllahir: String?,
    val email: String?,
    val email_verified_at: Int?,
    val id: Int?,
    val image: String?,
    val kota: String?,
    val name: String?,
    val phone: String?,
    val updated_at: String?,
    var tempat: Tempat?,
    val token: String?,
    var user_role: UserRole?,

) :Parcelable
{
    fun isAdmin()= user_role?.isAdmin ?: false
}
