package com.fy.main.navigate

import android.os.Bundle
import com.fy.navigation.BaseNavigator

class MainNavigationImpl : BaseNavigator(), MainNavigation {
    override fun navigateToXScreen(args: Bundle) {
       // navController.navigate(R.id.x, args)
    }
}
