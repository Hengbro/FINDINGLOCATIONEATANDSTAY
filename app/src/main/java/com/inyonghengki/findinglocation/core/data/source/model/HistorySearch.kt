package com.inyonghengki.findinglocation.core.data.source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistorySearch(
    val id: Int? = null,
    val userId: Int? = null,
    val name: String? = null,
    val isActive: Boolean? = null,
    val updated_at: String? = null,
    val created_at: String? = null,
    var user: User? = null,

) : Parcelable