package com.example.filmbookdiary.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.filmbookdiary.ui.screens.books.BooksScreen
import com.example.filmbookdiary.ui.screens.films.FilmsScreen
import com.example.filmbookdiary.ui.screens.films.SingleFilmScreen
import com.example.filmbookdiary.ui.screens.profile.ProfileScreen
import com.example.filmbookdiary.ui.screens.splash.SplashScreen

@Composable
fun DiaryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Splash.route,
        modifier = modifier
    ) {
        composable(route = Splash.route) {
            SplashScreen {
                navController.navigatePopUpTo(Films.route)
            }
        }

        composable(route = Films.route) {
            FilmsScreen(
                navigateToSingleFilm = { filmID ->
                    navController.navigateToSingleFilm(filmID.toString())
                }
            )
        }
        composable(route = Books.route) {
            BooksScreen()
        }
        composable(route = Profile.route) {
            ProfileScreen()
        }
        composable(
            route = SingleFilm.routeWithArgs,
            arguments = SingleFilm.arguments
        ) { navBackStackEntry ->
            val filmID =
                navBackStackEntry.arguments?.getString(SingleFilm.filmIDArg)
            SingleFilmScreen(filmID = filmID)
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }

fun NavHostController.navigatePopUpTo(route: String) {
    this.navigate(route) { popUpTo(0) { inclusive = false } }
}

private fun NavHostController.navigateToSingleFilm(filmType: String) {
    this.navigateSingleTopTo("${SingleFilm.route}/$filmType")
}
