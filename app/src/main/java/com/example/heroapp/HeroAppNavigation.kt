package com.example.heroapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.heroapp.Destinations.HERO_DETAIL_ROUTE
import com.example.heroapp.NavArguments.HERO_ARG
import com.example.heroapp.heroDetail.presentation.HeroDetailScreen
import com.example.heroapp.herolist.presentation.HeroListScreen

object Destinations {
    const val HERO_LIST_ROUTE = "heroList"
    const val HERO_DETAIL_ROUTE = "heroDetail/{$HERO_ARG}"
}

object NavArguments {
    const val HERO_ARG = "heroArg"
}

@ExperimentalCoilApi
@Composable
fun NavGraph(navController: NavHostController = rememberNavController(),) {
    NavHost(
        navController = navController,
        startDestination = Destinations.HERO_LIST_ROUTE
    ) {
        composable(Destinations.HERO_LIST_ROUTE) { HeroListScreen(navController = navController) }
        composable(
            route = HERO_DETAIL_ROUTE,
            arguments = listOf(navArgument(HERO_ARG) { type = NavType.IntType })
        ) { backStackEntry ->
            HeroDetailScreen(navController = navController)
        }
    }
}
