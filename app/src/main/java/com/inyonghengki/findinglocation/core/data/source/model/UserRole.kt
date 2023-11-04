package com.inyonghengki.findinglocation.core.data.source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserRole(
    val id: String? = null,
    val isAdmin: Boolean? = false
): Parcelable

