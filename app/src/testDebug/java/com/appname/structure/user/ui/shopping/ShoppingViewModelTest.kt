package com.appname.structure.user.ui.shopping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appname.structure.utils.MainCoroutineRule
import com.appname.structure.utils.getOrAwaitValue
import com.base.hilt.ui.shopping.ShoppingViewModel
import com.base.hilt.utils.Constants
import com.base.hilt.utils.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for the [ShoppingViewModel].
 */
@ExperimentalCoroutinesApi
class ShoppingViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setup() {
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empty field, returns error`() {
        viewModel.insertShoppingItem(name = "name", amountString = "", priceString = "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long name, returns error`() {
        val string = buildString {
            for(i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }
        viewModel.insertShoppingItem(name = string, amountString = "5", priceString = "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long price, returns error`() {
        val string = buildString {
            for(i in 1..Constants.MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }
        viewModel.insertShoppingItem(name = "name", "5", string)

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too high amount, returns error`() {
        viewModel.insertShoppingItem(name = "name", amountString = "9999999999999999999", priceString = "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid input, returns success`() {
        viewModel.insertShoppingItem(name = "name", amountString = "200", priceString = "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}
