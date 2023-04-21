package com.base.hilt.utils

import androidx.datastore.preferences.core.stringPreferencesKey
import com.base.hilt.BuildConfig
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object Constants {
    val JSON = jacksonObjectMapper()
    val PREF_NAME = BuildConfig.APPLICATION_ID
    var KEY_BUNDLE = "bundle"

    var KEY_DATA = "itemData"
    var KEY_GRAPH_ID = "graphId"
    var KEY_START_DESTINATION = "startDestination"

    object DataStore {
        val DATA = stringPreferencesKey("data")
        val SECURED_DATA = stringPreferencesKey("secured_data")
    }

    const val DATABASE_NAME = "shopping_db"

    const val BASE_URL = "https://pixabay.com"
    const val MOCK_BASE_URL = "https://demo1732035.mockable.io/"
    const val DEV_BASE_URL = "https://androidfirst.free.beeceptor.com/my/api/"

    const val MAX_NAME_LENGTH = 20
    const val MAX_PRICE_LENGTH = 10

    const val GRID_SPAN_COUNT = 4
}