package com.base.hilt.ui.login

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.base.hilt.MainActivity
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentLoginBinding
import com.base.hilt.model.response.BaseDataResponse
import com.base.hilt.network.ResponseData
import com.base.hilt.network.ResponseHandler
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created By Richa Shah on 2/08/2022 at 2:00 PM
 * Data store Usage Example
 */

@AndroidEntryPoint
class LoginFragment : FragmentBase<LoginViewModel, FragmentLoginBinding>() {
    override fun setupToolbar() {
        viewModel.setToolbarItems(
            ToolbarModel(
                isVisible = true,
                title = getString(R.string.login),
                isBottomNavVisible = false
            )
        )
    }

   /* @Inject
    lateinit var dataStore: DataStoreUtil*/

    override fun initializeScreenVariables() {
        dataBinding.viewModel = viewModel
        dataBinding.loginBtn.setOnClickListener {
            viewModel.onLoginClick(viewModel.email.value.toString(),viewModel.password.value.toString())
        }
        observeData()
    }

    private fun observeData() {
        viewModel.loginResponse.observe(this, Observer {
            if (it == null) {
                return@Observer
            }
            when (it) {
                is ResponseHandler.Loading -> {
                    viewModel.showProgressBar(true)
                }
                is ResponseHandler.OnFailed -> {
                    viewModel.showProgressBar(false)
                    httpFailedHandler(it.code, it.message, it.messageCode)
                }
                is ResponseHandler.OnSuccessResponse<ResponseData<BaseDataResponse>?> -> {
                    viewModel.showProgressBar(false)
                    findNavController().navigate(R.id.action_loginFragment_to_navigation_home)

                    when {
                        it.response?.data?.meta?.message?.isNotEmpty() == true -> {
                           /* lifecycleScope.launch {
                                dataStore.addValue(PrefKey.IS_LOGGED_IN, true)
                            }*/
//                            viewModel.showSnackBarMessage(R.string.label_login_successfully)
//                            view?.showSnackbar(R.string.label_login_successfully){}
//                            navigateToHomeScreen()
                        }
                        else -> {
                          /*  lifecycleScope.launch {
                                dataStore.addValue(PrefKey.IS_LOGGED_IN, false)
                            }*/
                        }
                    }

                }
            }
        })
    }
    private fun navigateToHomeScreen() {
       launchWithFirstScreen(
            MainActivity.newIntent(
                requireContext(),
                destination = R.id.navigation_home,
                graphId = R.navigation.mobile_navigation
            )
        )
    }
  /*  private fun onLoginClick() {
        val email = dataBinding.emailText
        val password = dataBinding.passwordText
        when {
            !Validation.isNotNull(email.text.toString().trim()) -> {
                view?.showSnackbar(R.string.alert_enter_email){}
            }
            !Validation.isEmailValid(email.text.toString().trim()) -> {
                view?.showSnackbar(R.string.alert_enter_valid_email) {}
            }
            !Validation.isNotNull(password.text.toString().trim()) -> {
                view?.showSnackbar(R.string.alert_enter_password) {}
            }
            !Validation.isValidPassword(password.text.toString().trim()) -> {
                view?.showSnackbar(R.string.alert_enter_valid_password) {}
            }
            else -> {
                viewModel.callLoginAPI()
            }
        }
    }*/


    override fun getViewModelClass(): Class<LoginViewModel> =
        LoginViewModel::class.java

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }


}