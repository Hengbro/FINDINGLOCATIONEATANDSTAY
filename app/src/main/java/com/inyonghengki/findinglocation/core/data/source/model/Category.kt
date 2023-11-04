package com.inyonghengki.findinglocation.core.data.source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int? = null,
    val parentId: Int? = null,
    val name: String,
    val description: String? = null,
    val image: String? = null,
    val isActive: Boolean? = null,
    val updated_at: String? = null,
    val created_at: String? = null
) : Parcelable