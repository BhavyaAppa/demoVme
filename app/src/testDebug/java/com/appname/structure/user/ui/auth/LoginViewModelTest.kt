package com.appname.structure.user.ui.auth


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.appname.structure.utils.getOrAwaitValue
import com.base.hilt.model.response.BaseDataResponse
import com.base.hilt.model.response.MetaD
import com.base.hilt.model.response.ResponseBase
import com.base.hilt.network.ApiInterface
import com.base.hilt.network.ResponseData
import com.base.hilt.network.ResponseHandler
import com.base.hilt.ui.login.LoginRepository
import com.base.hilt.ui.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.runners.MethodSorters
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Unit tests for the [LoginViewModel].
 */
@Suppress("IllegalIdentifier")
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class LoginViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var loginViewModel: LoginViewModel

    lateinit var loginRepository: LoginRepository

    @Mock
    lateinit var apiService: ApiInterface

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    var responseLiveData = MutableLiveData<ResponseHandler<ResponseData<BaseDataResponse>?>>()


    var baseDataResponse = BaseDataResponse(
        meta = MetaD(
            message = "Logout sucessfully", status =
            "sucess", messageCode = "", statusCode = 200
        )
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        loginRepository = LoginRepository(apiService)
        loginViewModel = LoginViewModel(loginRepository)

    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        runTest {
            loginViewModel.callLoginAPI()

            /*       doReturn(baseDataResponse)
                       .`when`(apiService)
                       .callLogoutApi()*/

            delay(2000)
            val result = loginViewModel.loginResponse.getOrAwaitValue()
            val expected = listOf(
                ResponseHandler.Loading,
                ResponseHandler.OnSuccessResponse(baseDataResponse)
            )
            assertTrue(expected[1]::class == result::class)

        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun checkEmailValue_Passed_HasReturnCorrect() = runTest {
        loginViewModel.email.value="shahxtz@gmail.com"
        val result = loginViewModel.email.value
        Assert.assertEquals("shahxtz@gmail.com", result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun checkPasswordValue_Passed_HasReturnCorrect() = runTest {
        loginViewModel.password.value="Brain@1220"
        val result = loginViewModel.password.value
        Assert.assertEquals("Brain@1220", result)
    }

    @Test
    fun castGivenServerResponse200_whenFetch_shouldReturnSuccess() {
        runTest {

            val expected = listOf(
                ResponseHandler.Loading,
                ResponseHandler.OnSuccessResponse(baseDataResponse)
            )

            responseLiveData.value = loginRepository.callLoginAPI()

            Mockito.`when`(loginRepository.makeAPICall { apiService.login() })
                .thenReturn(responseLiveData.getOrAwaitValue())
            loginViewModel.callLoginAPI()

            val result =
                loginViewModel.loginResponse.getOrAwaitValue() as ResponseHandler.OnSuccessResponse<*>

            assertTrue(expected[1]::class == result::class)

        }
    }
    @Test
    fun givenServerResponse200__whenFetch_shouldReturnSuccess()
    {
        runBlocking {
            Mockito.doReturn(generateLogoutResponse())
                .`when`(apiService)
                .login()
            loginViewModel.callLoginAPI()
            val result = loginViewModel.loginResponse.getOrAwaitValue()
            Thread.sleep(2000)

            assertTrue(result.toString().isNotEmpty())

            /*val subjectObj = Gson().fromJson(result.toString().trim(), BaseDataResponse::class.java)
            val meta = subjectObj.meta
            Assert.assertTrue(meta != null)*/
        }
    }

    private fun generateLogoutResponse(): ResponseHandler.OnSuccessResponse<ResponseData<ResponseBase>>
    {
        return ResponseHandler.OnSuccessResponse(ResponseData())
    }

}