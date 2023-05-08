package otus.homework.coroutines

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DiContainer {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val factService by lazy { retrofit.create(CatsServiceFact::class.java) }
    val imageService by lazy { retrofit.create(CatsServiceImage::class.java) }
}