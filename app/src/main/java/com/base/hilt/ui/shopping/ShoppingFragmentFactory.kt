package com.base.hilt.ui.shopping

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject

class ShoppingFragmentFactory @Inject constructor(
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            ImagePickFragment::class.java.name -> ImagePickFragment()
            else                               -> super.instantiate(classLoader, className)
        }
    }
}