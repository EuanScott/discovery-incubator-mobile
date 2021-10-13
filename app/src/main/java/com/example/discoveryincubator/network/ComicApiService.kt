package com.example.discoveryincubator.network

import com.example.discoveryincubator.models.Issue
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "http://10.0.2.2:8080/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

interface ComicApiService {
    @GET("issues")
    suspend fun getIssues(): List<Issue>

    @GET("issues/{name}")
    suspend fun getIssueByName(@Path("name") name: String): List<Issue>
}

object ComicApi {
    val retrofitService: ComicApiService by lazy {
        retrofit.create(ComicApiService::class.java)
    }
}
