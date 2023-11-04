package com.inyonghengki.findinglocation.core.data.source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rating(
    val id: Int? = null,
    val userId: Int? = null,
    val tempatId: Int? = null,
    val qtyReview: Int? = null,
    val cleanliness: Float? = null,
    val comfort: Float? = null,
    val location: Float? = null,
    val price: Float? = null,
    val review: String? = null,
    val service: Float? = null,
    val avgRating: Float? = null,
    val isActive: Boolean? = null,
    val created_at: String? = null,
    val updated_at: String? = null,
    var place: Tempat? = null,
    var user: User? = null,

) : Parcelable