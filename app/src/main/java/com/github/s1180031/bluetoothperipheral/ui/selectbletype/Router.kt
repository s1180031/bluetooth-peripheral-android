package com.github.s1180031.bluetoothperipheral.ui.selectbletype

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.lang.ref.WeakReference

abstract class Router(fragment: Fragment) {
    private var weakFragment = WeakReference(fragment)
    internal val navController = weakFragment.get()?.findNavController()
}
