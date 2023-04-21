package com.base.hilt.ui.splash

import android.os.Bundle
import androidx.navigation.findNavController
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.base.ViewModelBase
import com.base.hilt.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : FragmentBase<ViewModelBase, FragmentSplashBinding>() {


   /* @Inject
    lateinit var dataStoreUtil: DataStoreUtil*/

    override fun getLayoutId(): Int {
        return R.layout.fragment_splash
    }

    override fun setupToolbar() {
        viewModel.setToolbarItems(
            ToolbarModel(
                isVisible = false,
                title = null,
                isBottomNavVisible = false
            )
        )
    }

    override fun getViewModelClass(): Class<ViewModelBase> = ViewModelBase::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun initializeScreenVariables() {
//        dataStoreUtil= DataStoreUtil(requireContext(), SecurityUtil())

    }

    override fun onResume() {
        super.onResume()
        val isUserLoggedIn=true
        /*lifecycleScope.launch {
            dataStoreUtil.userLoggedIn(true)
           isUserLoggedIn= dataStoreUtil.isUserLoggedIn.first()

        }*/
      /*  lifecycleScope.launch(context = Dispatchers.Main) {
            if (isUserLoggedIn)
            {
                val action = SplashFragmentDirections.actionSplashFragmentToNavigationHome()
                dataBinding.main.findNavController().navigate(action)
            }else
            {
                val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                dataBinding.main.findNavController().navigate(action)
            }*/

        if (isUserLoggedIn)
        {
            val action = SplashFragmentDirections.actionSplashFragmentToNavigationHome()
            dataBinding.main.findNavController().navigate(action)
        }else
        {
            val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
            dataBinding.main.findNavController().navigate(action)
        }
        }
    }

