package com.example.filmbookdiary.nav

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.filmbookdiary.R

interface DiaryDestination {
    val icon: Int
    val route: String
}

object Splash: DiaryDestination {
    override val icon = R.drawable.round_person_24
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
    override val icon = R.drawable.outline_ondemand_video_24
    override val route = "singleFilm"
    const val filmIDArg = "film_id"
    val routeWithArgs = "${route}/{${filmIDArg}}"
    val arguments = listOf(
        navArgument(filmIDArg) { type = NavType.StringType }
    )
}

object SingleBook: DiaryDestination {
    override val icon = R.drawable.round_menu_book_24
    override val route = "singleBook"
    const val bookIDArg = "book_id"
    val routeWithArgs = "${route}/{${bookIDArg}}"
    val arguments = listOf(
        navArgument(bookIDArg) { type = NavType.StringType }
    )
}

val diaryTabRowScreens = listOf(Films, Books, Profile)
val diaryAllScreens = listOf(Films, Books, Profile, Splash, SingleFilm, SingleBook)