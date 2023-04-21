package com.appname.structure.user.ui.shopping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.appname.structure.user.utils.MainActivityTestRule
import com.appname.structure.user.utils.getOrAwaitValue
import com.appname.structure.user.utils.launchFragmentInHiltContainer
import com.appname.structure.user.utils.waitFor
import com.base.hilt.R.id
import com.base.hilt.model.local.ShoppingItem
import com.base.hilt.ui.shopping.AddShoppingItemFragment
import com.base.hilt.ui.shopping.ShoppingViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import javax.inject.Inject


/**
 * Unit tests for the [AddShoppingItemFragment].
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class AddShoppingItemFragmentTestTwo {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: TestShoppingFragmentFactory

    @get:Rule(order = 1)
    var activityRule = MainActivityTestRule(id.addShoppingItemFragment)


    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickInsertIntoDb_shoppingItemInsertedIntoDb() {
        val testViewModel = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
        launchFragmentInHiltContainer<AddShoppingItemFragment>(
            fragmentFactory = fragmentFactory
        ) {
            //TODO
            mViewModel = testViewModel
        }

        Espresso.onView(withId(id.etShoppingItemName))
            .perform(ViewActions.replaceText("shopping item"))
        Espresso.onView(withId(id.etShoppingItemAmount)).perform(ViewActions.replaceText("5"))
        Espresso.onView(withId(id.etShoppingItemPrice)).perform(ViewActions.replaceText("5.5"))
        onView(isRoot()).perform(waitFor(3000))

        Espresso.onView(withId(id.btnAddShoppingItem)).perform(ViewActions.click())

        verify(testViewModel.shoppingItems.getOrAwaitValue())
            .contains(ShoppingItem("shopping item", 5, 5.5f, ""))
    }

    @Test
    fun searchApi_Testing() {
        val testViewModel = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
        launchFragmentInHiltContainer<AddShoppingItemFragment>(
            fragmentFactory = fragmentFactory
        ) {
            mViewModel = testViewModel
            CoroutineScope(Dispatchers.IO).launch {
                delay(500L)
            }
        }
        onView(isRoot()).perform(waitFor(3000))


//        assertThat(testViewModel.images.getOrAwaitValue()).isNotNull()
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment>(
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        verify(navController).popBackStack()
    }
}