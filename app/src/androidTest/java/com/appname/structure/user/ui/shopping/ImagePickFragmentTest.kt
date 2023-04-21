package com.appname.structure.user.ui.shopping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.appname.structure.user.utils.getOrAwaitValue
import com.appname.structure.user.utils.launchFragmentInHiltContainer
import com.appname.structure.user.utils.waitFor
import com.base.hilt.R
import com.base.hilt.ui.shopping.ImageAdapter
import com.base.hilt.ui.shopping.ImagePickFragment
import com.base.hilt.ui.shopping.ShoppingViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImagePickFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()

    }


    @Test
    fun clickImage_popBackStackedSetImageUrl() {
        val navController = Mockito.mock(NavController::class.java)
        val testUrl = "https://picsum.photos/200"
        val testViewModel = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
        launchFragmentInHiltContainer<ImagePickFragment> {
            Navigation.setViewNavController(requireView(), navController)
            imageAdapter.images = arrayListOf(testUrl, testUrl)
            mViewModel = testViewModel

        }

        onView(withId(R.id.rvImages)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<ImageAdapter
                .ImageViewHolder>(
                    0,
                    click()
                )
        )
        onView(ViewMatchers.isRoot()).perform(waitFor(2000))

        Mockito.verify(navController).popBackStack()


    }

    @Test
    fun searchApi_Testing() {
        val navController = Mockito.mock(NavController::class.java)
//        val testUrl = "https://picsum.photos/200"
        val testViewModel = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
        launchFragmentInHiltContainer<ImagePickFragment> {
            Navigation.setViewNavController(requireView(), navController)
//            imageAdapter.images = arrayListOf(testUrl, testUrl)
            mViewModel = testViewModel
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.searchForImage("a")
                delay(2000)
            }

        }

        onView(ViewMatchers.isRoot()).perform(waitFor(2000))

        Assert.assertTrue(
            testViewModel.images.getOrAwaitValue().getContentIfNotHandled()?.data != null
        )

    }

    @After
    fun tearDown() {
    }


}