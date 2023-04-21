package com.base.hilt.model.response


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class BaseDataResponse(
    @JsonProperty("meta")
    var meta: MetaD? = null
)


data class MetaD(
    @JsonProperty("message")
    var message: String? = null,
    @JsonProperty("message_code")
    var messageCode: String? = null,
    @JsonProperty("status")
    var status: String? = null,
    @JsonProperty("status_code")
    var statusCode: Int? = null
)
class ResponseBase {
}