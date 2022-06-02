package com.assessment.mobileengineerassesment.model

import com.squareup.moshi.Json

data class ImageUrls(
    @field:Json(name = "full")
    val full: String,
    @field:Json(name = "raw")
    val raw: String,
    @field:Json(name = "regular")
    val regular: String,
    @field:Json(name = "small")
    val small: String,
    @field:Json(name = "small_s3")
    val smallS3: String,
    @field:Json(name = "thumb")
    val thumb: String
)
