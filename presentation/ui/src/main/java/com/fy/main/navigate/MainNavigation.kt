package com.fy.main.navigate

import android.os.Bundle
import com.fy.navigation.Navigation

interface MainNavigation : Navigation {
    fun navigateToXScreen(args: Bundle)
}
