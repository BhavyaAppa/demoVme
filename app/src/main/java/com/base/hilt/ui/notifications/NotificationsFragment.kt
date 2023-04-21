package com.base.hilt.ui.notifications

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.base.hilt.MainActivity
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.LocaleManager
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentNotificationsBinding
import com.base.hilt.utils.DataStoreUtil
import com.base.hilt.utils.MyPreference
import com.base.hilt.utils.PrefKey
import com.base.hilt.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsFragment : FragmentBase<NotificationsViewModel, FragmentNotificationsBinding>() {

    @Inject
    lateinit var localeManager: LocaleManager

    @Inject
    lateinit var mPref: MyPreference

    @Inject
    lateinit var dataStoreUtil: DataStoreUtil

    override fun getLayoutId(): Int {
        return R.layout.fragment_notifications
    }

    override fun setupToolbar() {
        viewModel.setToolbarItems(ToolbarModel(true, "Change Language", true))
    }

    override fun initializeScreenVariables() {

        dataBinding.viewModel = viewModel
        when (mPref.getValueString(
            PrefKey.SELECTED_LANGUAGE,
            PrefKey.EN_CODE
        )) {
            PrefKey.EN_CODE ->
                dataBinding.btnEnglish.isChecked = true
            else ->
                dataBinding.btnArabic.isChecked = true
        }
        observeData()
        setupLongRunningTask()
    }

    private fun observeData() {
        viewModel.changeLanguageClick.observe(this) {
            if (dataBinding.btnEnglish.isChecked) {
//                mPref.setValueString(PrefKey.SELECTED_LANGUAGE, PrefKey.EN_CODE)
                lifecycleScope.launch {
                    dataStoreUtil.addValue(PrefKey.SELECTED_LANGUAGE, PrefKey.EN_CODE)
                }

            } else {
//                mPref.setValueString(PrefKey.SELECTED_LANGUAGE, PrefKey.AR_CODE)
                lifecycleScope.launch {
                    dataStoreUtil.addValue(PrefKey.SELECTED_LANGUAGE, PrefKey.AR_CODE)
                }
            }
            if (dataBinding.btnLogin.isChecked) {
//                mPref.setValueString(PrefKey.SELECTED_LANGUAGE, PrefKey.EN_CODE)
                lifecycleScope.launch {
                    dataStoreUtil.userLoggedIn(true)
                }

            } else {
                lifecycleScope.launch {
                    dataStoreUtil.userLoggedIn(false)
                }
            }
            /*  localeManager.setNewLocale(
                  activity as MainActivity,
                  mPref.getValueString(PrefKey.SELECTED_LANGUAGE, PrefKey.AR_CODE).toString()
              )*/

            lifecycleScope.launch {
                dataStoreUtil.retrieveValue(PrefKey.SELECTED_LANGUAGE, String::class)
                    .collect {
                        localeManager.setNewLocale(
                            activity as MainActivity,
                            it.toString()
                        )
                    }
            }

            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun setupLongRunningTask() {
        viewModel.getStatus().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    viewModel.showProgressBar(false)
                    viewModel.showSnackBarMessage(it.data.toString())
                }
                Status.LOADING -> {
                    viewModel.showProgressBar(true)
                }
                Status.ERROR -> {
                    //Handle Error
                    viewModel.showProgressBar(false)
                    viewModel.showSnackBarMessage(it.message.toString())
                }
            }
        }
        viewModel.startLongRunningTask()
    }

    override fun getViewModelClass(): Class<NotificationsViewModel> =
        NotificationsViewModel::class.java

}