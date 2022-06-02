package com.assessment.mobileengineerassesment.model

import com.squareup.moshi.Json

data class ImageSearchQuery(
    @field:Json(name = "order_by")
    var orderBy: String? = "latest",
)
