package com.fy.navigation

import androidx.navigation.NavController

abstract class BaseNavigator : Navigation {
   protected lateinit var navController: NavController

    override fun bind(navController: NavController) {
        this.navController = navController
    }

}
