package com.assessment.mobileengineerassesment.model

import com.squareup.moshi.Json

data class UserProfileImage(
    @field:Json(name = "large")
    val large: String,
    @field:Json(name = "medium")
    val medium: String,
    @field:Json(name = "small")
    val small: String
)
