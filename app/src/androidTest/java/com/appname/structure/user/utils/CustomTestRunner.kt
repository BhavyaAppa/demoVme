package com.appname.structure.user.utils

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.CustomTestApplication

/**
 * A custom [AndroidJUnitRunner] used to replace the application used in tests. Note that Hilt
 * generates a [CustomTestRunner_Application] based on the the [MainTestApplication] defined in
 * the [CustomBaseTestApplication] annotation.
 */
@CustomTestApplication(MainTestApplication::class)
class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, CustomTestRunner_Application::class.java.name, context)
    }
}
