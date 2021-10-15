package com.example.discoveryincubator.network

import com.example.discoveryincubator.models.Issue
import com.example.discoveryincubator.services.IssueSearch
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

private const val BASE_URL = "http://10.0.2.2:8080/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    .build()


interface ComicApiService {
    @POST("issues")
    fun getIssues(@Body issueSearch: IssueSearch): Observable<List<Issue>>
}

object ComicApi {
    val retrofitService: ComicApiService by lazy {
        retrofit.create(ComicApiService::class.java)
    }
}
