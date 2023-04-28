package com.example.filmbookdiary.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.filmbookdiary.R

interface DiaryDestination {
    val icon: Int
    val route: String
}

object Splash: DiaryDestination {
    override val icon: Int
        get() = TODO("Not yet implemented")

    override val route = "splash"
}

object Films : DiaryDestination {
    override val icon = R.drawable.outline_ondemand_video_24
    override val route = "films"
}

object Books : DiaryDestination {
    override val icon = R.drawable.round_menu_book_24
    override val route = "books"
}

object Profile : DiaryDestination {
    override val icon = R.drawable.round_person_24
    override val route = "profile"
}

object SingleFilm: DiaryDestination {
    override val icon: Int
        get() = TODO("Not yet implemented")
    override val route = "singleFilm"
    const val filmIDArg = "film_id"
    val routeWithArgs = "${route}/{${filmIDArg}}"
    val arguments = listOf(
        navArgument(filmIDArg) { type = NavType.StringType }
    )
}

object SingleBook: DiaryDestination {
    override val icon: Int
        get() = TODO("Not yet implemented")
    override val route = "singleBook"
    const val bookIDArg = "book_id"
    val routeWithArgs = "${route}/{${bookIDArg}}"
    val arguments = listOf(
        navArgument(bookIDArg) { type = NavType.StringType }
    )
}

val diaryTabRowScreens = listOf(Films, Books, Profile)
val diaryAllScreens = listOf(Films, Books, Profile, Splash, SingleFilm, SingleBook)