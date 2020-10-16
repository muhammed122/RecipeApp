

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val BASE_URL = "https://hf-android-app.s3-eu-west-1.amazonaws.com/"
//const val BASE_URL = "https://jsonplaceholder.typicode.com/"

object ApiManager {


        val apiClient: RecipeService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RecipeService::class.java)


}