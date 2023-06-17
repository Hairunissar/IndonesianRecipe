package org.d3if3058.myapplication.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if3058.myapplication.model.Resep
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/Hairunissar/IndonesianRecipe/main/makananindonesiaaaa/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface ResepApiService {
    @GET("static-api.json")
    suspend fun getResep(): List<Resep>
}
object ResepApi {
    val service: ResepApiService by lazy {
        retrofit.create(ResepApiService::class.java)

    }

    fun getResepUrl(nama: String): String {
        return "$BASE_URL/$nama"
    }
}
enum class ApiStatus { LOADING, SUCCESS, FAILED }
