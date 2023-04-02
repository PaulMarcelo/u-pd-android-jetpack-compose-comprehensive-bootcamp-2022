package ec.com.pmyb.movieapp.model

data class Movie(
    val id: String,
    val title: String,
    val year: String,
    val genre: String,
    val director: String,
    val actors: String,
    val plot: String,
    val poster: String,
    val images: List<String>,
    val rating: String,
)

fun getMovies(): List<Movie> {
    return listOf(
        Movie(
            id = "tt0499549",
            title = "Avatar",
            year = "2009",
            genre = "Action",
            director = "James cameron",
            actors = "Zoe Saldana",
            plot = "A paraplegic marine dispatche..",
            poster = "https://lumiere-a.akamaihd.net/v1/images/auyt_20cs_avatarrerelease_thumbnail_94e3d3d8.jpeg?region=10,7,1057,596&width=768",
            images = listOf("https://static2.abc.es/media/play/2020/09/29/avatar-kE4H--1024x512@abc.jpeg",
            "https://i0.wp.com/imgs.hipertextual.com/wp-content/uploads/2020/01/hipertextual-arte-conceptual-avatar-2-muestra-nuevos-rincones-pandora-2020623395.jpg?fit=2048%2C1295&quality=50&strip=all&ssl=1",
            "https://lumiere-a.akamaihd.net/v1/images/3_avtr-460_2647266a.jpeg?region=0,0,1920,1080&width=960"),
            rating = "7.9"
        )
    )
}
