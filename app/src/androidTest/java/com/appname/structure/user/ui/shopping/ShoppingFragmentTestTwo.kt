package com.appname.structure.user.ui.shopping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.appname.structure.user.utils.MainActivityTestRule
import com.appname.structure.user.utils.getOrAwaitValue
import com.appname.structure.user.utils.launchFragmentInHiltContainer
import com.base.hilt.R
import com.base.hilt.model.local.ShoppingItem
import com.base.hilt.ui.shopping.ShoppingFragment
import com.base.hilt.ui.shopping.ShoppingItemAdapter
import com.base.hilt.ui.shopping.ShoppingViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

/**
 * Unit tests for the [ShoppingFragment].
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ShoppingFragmentTestTwo {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityRule = MainActivityTestRule(R.id.navigation_shopping)

    @get:Rule(order = 2)
     var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
        // Click on the icon - we can find it by the r.Id.
        onView(withId(R.id.navigation_shopping))
            .perform(ViewActions.click());
    }

    //todo
    @Ignore("not working now")
    @Test
    fun swipeShoppingItem_deleteItemInDb() {
        val shoppingItem = ShoppingItem("TEST", 1, 1f, "TEST", 1)
        var testViewModel: ShoppingViewModel? = null
        launchFragmentInHiltContainer<ShoppingFragment>(
        ) {
            testViewModel = viewModel
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.insertShoppingItemIntoDb(shoppingItem)
            }
        }

        onView(withId(R.id.rvShoppingItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ShoppingItemAdapter.ShoppingItemViewHolder>(
                0,
                ViewActions.swipeLeft()
            )
        )
        CoroutineScope(Dispatchers.IO).launch {
            assertThat(testViewModel?.shoppingItems?.getOrAwaitValue()).isEmpty()
        }
    }

    @Test
    fun clickAddShoppingItemButton_navigateToAddShoppingItemFragment() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ShoppingFragment>(
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.fabAddShoppingItem)).perform(ViewActions.click())
//        onView(ViewMatchers.isRoot()).perform(waitFor(3000))

        Mockito.verify(navController).navigate(
            R.id.action_shoppingFragment_to_addShoppingItemFragment
        )
    }
}

