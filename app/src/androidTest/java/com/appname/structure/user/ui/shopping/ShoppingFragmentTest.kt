package com.appname.structure.user.ui.shopping

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.appname.structure.user.utils.MainActivityTestRule
import com.appname.structure.user.utils.launchFragmentInHiltContainer
import com.appname.structure.user.utils.recyclerViewSizeMatcher
import com.base.hilt.R
import com.base.hilt.ui.shopping.ShoppingFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.util.concurrent.TimeUnit.MINUTES

/**
 * Unit tests for the [ShoppingFragment].
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ShoppingFragmentTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityRule = MainActivityTestRule(R.id.navigation_shopping)

    @get:Rule(order = 2)
    var globalTimeout: Timeout = Timeout(52, MINUTES)

    @Before
    fun setup() {
        hiltRule.inject()
        // Click on the icon - we can find it by the r.Id.
        onView(withId(R.id.navigation_shopping))
            .perform(click());
    }


    // Check that the title is correct
    @Test(timeout = 50000)
    fun checkTitle_shopping_screen() {
        Thread.sleep(3000)
        onView(withId(R.id.fabAddShoppingItem)).perform(click())

        onView(
            CoreMatchers.allOf(
                withId(R.id.toolbarTitle),
                ViewMatchers.withText(R.string.title_add_shopping)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


    }


    @Test(timeout = 500000)
    fun testRecyclerview_HasSpecificSize() {
        Thread.sleep(10000)
        // Get total item of RecyclerView
        val recyclerView: RecyclerView =
            activityRule.activity.findViewById(R.id.rvShoppingItems)
        val itemCount = recyclerView.adapter!!.itemCount

        onView(withId(R.id.rvShoppingItems)).check(
            ViewAssertions.matches(
                recyclerViewSizeMatcher(
                    itemCount
                )
            )
        )

    }


    @Ignore("Not working right now")
    @Test(timeout = 50000)
    fun clickAddShoppingItemButton_navigateToAddShoppingItemFragment() {
        Thread.sleep(3000)
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<ShoppingFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        Thread.sleep(3000)

        onView(withId(R.id.fabAddShoppingItem)).perform(click())
        verify(navController).navigate(
            R.id.action_shoppingFragment_to_addShoppingItemFragment
        )
    }
}

