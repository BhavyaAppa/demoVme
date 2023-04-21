package com.appname.structure.user.utils

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions
import com.base.hilt.HiltTestActivity
import com.base.hilt.MainActivity
import com.base.hilt.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * launchFragmentInContainer from the androidx.fragment:fragment-testing library
 * is NOT possible to use right now as it uses a hardcoded Activity under the hood
 * (i.e. [EmptyFragmentActivity]) which is not annotated with @AndroidEntryPoint.
 *
 * As a workaround, use this function that is equivalent. It requires you to add
 * [HiltTestActivity] in the debug folder and include it in the debug AndroidManifest.xml file
 * as can be found in this project.
 */
@ExperimentalCoroutinesApi
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    fragmentFactory: FragmentFactory? = null,
    crossinline action: T.() -> Unit = {}
) {
    val mainActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    )

    ActivityScenario.launch<HiltTestActivity>(mainActivityIntent).onActivity { activity ->
        fragmentFactory?.let {
            activity.supportFragmentManager.fragmentFactory = it
        }
        val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )
        fragment.arguments = fragmentArgs

        activity.supportFragmentManager.beginTransaction()
            .add(android.R.id.content, fragment, "")
            .commitNow()

        (fragment as T).action()
    }

}

inline fun <reified T : Fragment> launchFragmentInHiltContainerr(
    navController: NavController,
    navHost: Int= R.id.nav_host_fragment_activity_main,
    navGraph: Int=R.navigation.mobile_navigation,
): ActivityScenario<MainActivity> {
    val startActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            MainActivity::class.java
        )
    )
    return ActivityScenario.launch<MainActivity>(startActivityIntent).onActivity { activity ->
        val navHostFragment =
            activity.supportFragmentManager.findFragmentById(navHost) as NavHostFragment
        Navigation.setViewNavController(navHostFragment.requireView(), navController)
        navController.setGraph(navGraph)
    }
}

