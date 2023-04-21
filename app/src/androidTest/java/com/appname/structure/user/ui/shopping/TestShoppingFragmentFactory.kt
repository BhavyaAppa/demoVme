package com.appname.structure.user.ui.shopping

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.base.hilt.ui.shopping.AddShoppingItemFragment
import com.base.hilt.ui.shopping.ImagePickFragment
import com.base.hilt.ui.shopping.ShoppingFragment
import javax.inject.Inject

class TestShoppingFragmentFactory @Inject constructor(
) : FragmentFactory()
{

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment
    {
        return when (className)
        {
            ImagePickFragment::class.java.name       -> ImagePickFragment()
            AddShoppingItemFragment::class.java.name -> AddShoppingItemFragment()
            ShoppingFragment::class.java.name        -> ShoppingFragment(
            )
            else                                     -> super.instantiate(classLoader, className)
        }
    }
}