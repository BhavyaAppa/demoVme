package com.base.hilt.model.response


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class LocationResp : ArrayList<LocationRespItem>()
data class LocationRespItem(
    @JsonProperty("icon")
    var icon: String? = null,
    @JsonProperty("latLng")
    var latLng: LatLng? = null,
    @JsonProperty("primaryText")
    var primaryText: String? = null,
    @JsonProperty("secondaryText")
    var secondaryText: String? = null
)

data class LatLng(
    @JsonProperty("latitude")
    var latitude: String? = null,
    @JsonProperty("longitude")
    var longitude: String? = null
)
