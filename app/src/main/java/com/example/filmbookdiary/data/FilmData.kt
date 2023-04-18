package com.example.filmbookdiary.data

import android.net.Uri
import com.example.filmbookdiary.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object FilmData {
    private val films: MutableList<Film> = mutableListOf(
        Film(
            Uri.parse("android.resource://com.example.filmbookdiary/" + R.drawable.drive_photo),
            "Drive",
            "Вступительная сцена “Драйва” является изящным и подчеркивающим введением к тому, чем должна быть остальная часть фильма – напряженным, но продуманным экшн-фильмом об экстремальном вождении автомобиля. Вступительная сцена - одна из лучших в фильме, и это одна из немногих сцен, которые можно назвать запоминающимися. Но это не значит, что “Драйв” - плохой фильм. Под хитроумной и стильной оболочкой скрывается довольно простой и обычный боевик. Начиная с главного героя и заканчивая развитием сюжета, все происходит в довольно размеренном темпе с прямым повествованием."
        ),
        Film(
            Uri.parse("android.resource://com.example.filmbookdiary/" + R.drawable.barbie_photo),
            "Barbie",
            "Райан Гослинг играет водителя-каскадера в кино, который подрабатывает водителем для ограбления. Диалоги Гослинга скудны, и от него требуется раскрывать своего персонажа в основном с помощью выражений и действий. Мы никогда не получаем никакой справочной информации о нем, и его характер на самом деле не так уж хорошо проработан. Но в каком-то смысле это интригует людей. Зрителям дается возможность делать свои собственные выводы, основанные на ассоциациях, случайных склонностях к насилию и его сострадании к Ирен (Кэри Маллиган), соседке из его многоквартирного дома, с которой у него завязываются отношения. Их отношения состоят из нескольких сцен, где они смотрят друг на друга и ухмыляются, а также время от времени совершают послеобеденную прогулку. Ирен воспитывает своего маленького сына, пока ее муж находится в тюрьме, и водитель мгновенно привязывается к ним обоим."
        ),
        Film(
            Uri.parse("android.resource://com.example.filmbookdiary/" + R.drawable.lalaland_photo),
            "Lalaland",
            "I watched it yesterday and"
        ),
        Film(
            Uri.parse("android.resource://com.example.filmbookdiary/" + R.drawable.niceguys_photo),
            "Nice guys",
            "The very nice film, you know"
        )
    )

    fun getFilms(): Flow<List<Film>> = MutableStateFlow(films)

    fun getFilmsNotFlow(): List<Film> = films

    fun getFilm(filmName: String?): Film = films.first{ it.name == filmName }

    fun addFilm(film: Film) = films.add(film)

    fun deleteFilm(film: Film) = films.remove(film)

}

data class Film(
    val imageUri: Uri,
    val name: String,
    val description: String
)

