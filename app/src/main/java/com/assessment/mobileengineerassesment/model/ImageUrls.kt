package com.assessment.mobileengineerassesment.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageUrls(
    @field:Json(name = "full")
    val full: String,
    @field:Json(name = "regular")
    val regular: String,
    @field:Json(name = "small")
    val small: String,
    @field:Json(name = "thumb")
    val thumb: String
) : Parcelable
