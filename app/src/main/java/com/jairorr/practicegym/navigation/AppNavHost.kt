package com.jairorr.practicegym.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jairorr.practicegym.domain.UserDao
import com.jairorr.practicegym.presentation.HomeScreen
import com.jairorr.practicegym.presentation.HomeViewModel
import com.jairorr.practicegym.presentation.SecondaryScreen
import com.jairorr.practicegym.presentation.SecondaryViewModel
import com.jairorr.practicegym.presentation.transition.CardTransitionScreen
import com.jairorr.practicegym.presentation.transition.CardViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route,
    onChangeRoute:(String)->Unit,
    userDao:UserDao
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Home.route) {
            onChangeRoute("Home Screen")
            val homeViewModel = HomeViewModel(userDao)
            var listUserFromDb = homeViewModel.listUser.collectAsState().value
            HomeScreen(
                modifier = modifier,
                navigateToSecond = { userId -> navController.navigate("${NavigationItem.Secondary.route}/$userId") },
                listUser = listUserFromDb,
                saveUser = homeViewModel::saveUser,
                navigateToTransition = { navController.navigate(NavigationItem.CardTransition.route) }
            )
        }
        composable("${NavigationItem.Secondary.route}/{userId}") { backStackEntry ->
            onChangeRoute("Secondary Screen")
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
            val secondaryViewModel = SecondaryViewModel(userDao = userDao, uid = userId?:0)
            SecondaryScreen(
                viewModel = secondaryViewModel,
                backToHome = { navController.navigate(NavigationItem.Home.route) }
            )
        }
        composable(NavigationItem.CardTransition.route){
            val cardViewModel = CardViewModel()
            CardTransitionScreen(viewModel = cardViewModel)
        }
    }
}