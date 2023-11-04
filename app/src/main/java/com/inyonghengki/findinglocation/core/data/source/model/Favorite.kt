package com.inyonghengki.findinglocation.core.data.source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favorite(

    val id: Int? = null,
    val userId: Int? = null,
    val placeId: Int? = null,
    val isActive: Boolean? = null,
    val note: String? = null,
    val updated_at: String? = null,
    val created_at: String? = null,
    var place: Tempat? = null,
    var user: User? = null

): Parcelable