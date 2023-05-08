package otus.homework.coroutines

import retrofit2.http.GET

interface CatsServiceFact {

    @GET("fact")
    suspend fun getCatFact(): Fact
}
interface CatsServiceImage {

        @GET("search")
        suspend fun getCatImage() : List<Image>
    }