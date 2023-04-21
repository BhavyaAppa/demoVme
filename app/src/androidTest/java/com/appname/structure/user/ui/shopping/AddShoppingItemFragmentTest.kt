package com.appname.structure.user.ui.shopping

import androidx.navigation.NavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.appname.structure.user.utils.MainActivityTestRule
import com.base.hilt.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify



/**
 * Unit tests for the [AddShoppingItemFragment].
 */
@LargeTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class AddShoppingItemFragmentTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityRule = MainActivityTestRule(R.id.addShoppingItemFragment)

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun pressButton_popBackstack() {
        val navController = Mockito.mock(NavController::class.java)

      /*      launchFragmentInHiltContainer<AddShoppingItemFragment> {
                Navigation.setViewNavController(requireView(), navController)
            }*/
        pressBack()

        verify(navController).popBackStack()
    }

    @Test
    fun clickAddImageButton_navigateToAddImageFragment() {
        runTest {
            val navController = Mockito.mock(NavController::class.java)

            /*  launchFragmentInHiltContainer<AddShoppingItemFragment> {
                  Navigation.setViewNavController(requireView(), navController)
              }*/
            Espresso.onView(ViewMatchers.withId(R.id.ivShoppingImg)).perform(ViewActions.click())
            delay(200)
            /* verify(navController).navigate(
                 R.id.action_addShoppingItemFragment_to_imagePickFragment
             )*/
        }
    }
}