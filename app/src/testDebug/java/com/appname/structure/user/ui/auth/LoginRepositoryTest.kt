package com.appname.structure.user.ui.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appname.structure.utils.getOrAwaitValue
import com.base.hilt.base.BaseRepository
import com.base.hilt.model.response.BaseDataResponse
import com.base.hilt.model.response.MetaD
import com.base.hilt.network.ApiInterface
import com.base.hilt.network.ResponseData
import com.base.hilt.network.ResponseHandler
import com.base.hilt.ui.login.LoginRepository
import com.base.hilt.ui.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Unit tests for the [LoginRepository].
 */
@RunWith(JUnit4::class)
class LoginRepositoryTest : BaseRepository() {

    private lateinit var loginRepository: LoginRepository
    var baseDataResponse = BaseDataResponse(
        meta = MetaD(
            message = "Logout sucessfully", status =
            "sucess", messageCode = "", statusCode = 200
        )
    )
    lateinit var loginViewModel: LoginViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var apiService: ApiInterface

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        loginRepository = LoginRepository(apiService)
        loginViewModel = LoginViewModel(loginRepository)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `login test`() {
        runTest {
            loginViewModel.callLoginAPI()
            val expected = listOf(
                ResponseHandler.Loading,
                ResponseHandler.OnSuccessResponse(baseDataResponse)
            )
            val result =
                loginViewModel.loginResponse.getOrAwaitValue() as ResponseHandler.OnSuccessResponse<ResponseData<BaseDataResponse>?>

            Assert.assertTrue(expected[1]::class == result::class)
        }

    }

}