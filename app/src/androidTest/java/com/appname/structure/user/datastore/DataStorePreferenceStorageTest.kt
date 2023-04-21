package com.appname.structure.user.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.core.app.ApplicationProvider
import com.base.hilt.utils.DataStoreUtil
import com.base.hilt.utils.PreferenceStorage
import com.base.hilt.utils.SecurityUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

val Context.dataStore by preferencesDataStore(name = "test")

class DataStorePreferenceStorageTest {

    private lateinit var context: Context
    private lateinit var preferenceStorage: PreferenceStorage
    private lateinit var securityUtil: SecurityUtil

    @Before
    fun init() {
        context = ApplicationProvider.getApplicationContext()
        securityUtil= SecurityUtil()
        preferenceStorage = DataStoreUtil(context.dataStore, securityUtil)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun userLoggedIn() = runTest {
        preferenceStorage.userLoggedIn(true)
        val result = preferenceStorage.isUserLoggedIn.first()
        assertTrue(result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun checkData() = runTest {
        preferenceStorage.setSecuredData("Richa S")
        val result = preferenceStorage.getSecuredData().first()
        assertEquals("Richa S",result)
    }
}
