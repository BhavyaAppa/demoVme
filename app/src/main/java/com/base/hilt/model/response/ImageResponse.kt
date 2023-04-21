package com.base.hilt.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ImageResponse(
    val hits: List<ImageResult>,
    val total: Int,
    val totalHits: Int
)