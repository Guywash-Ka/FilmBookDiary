package com.example.filmbookdiary.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface DiaryDestination {
    val icon: ImageVector
    val route: String
}

object Films : DiaryDestination {
    override val icon = Icons.Rounded.PlayArrow
    override val route = "films"
}

object Books : DiaryDestination {
    override val icon = Icons.Rounded.Search
    override val route = "books"
}

object Profile : DiaryDestination {
    override val icon = Icons.Rounded.Person
    override val route = "profile"
}

object SingleFilm: DiaryDestination {
    override val icon: ImageVector
        get() = TODO("Not yet implemented")//=
    override val route = "singleFilm"
    const val filmIDArg = "film_id"
    val routeWithArgs = "${route}/{${filmIDArg}}"
    val arguments = listOf(
        navArgument(filmIDArg) { type = NavType.StringType }
    )
}

val diaryTabRowScreens = listOf(Films, Books, Profile)