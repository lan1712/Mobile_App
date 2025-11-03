package com.example.smart_uth_app.data.network

import com.example.smart_uth_app.data.model.Task
import com.example.smart_uth_app.data.model.TasksResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("researchUTH/tasks")
    suspend fun getTasks(): TasksResponse

    @GET("researchUTH/task/{id}")
    suspend fun getTaskDetail(@Path("id") id: Int): Task

    @DELETE("researchUTH/task/{id}")
    suspend fun deleteTask(@Path("id") id: Int)
}

object ApiClient {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }
    private val http = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://amock.io/api/")     // <-- base của 3 endpoint
            .addConverterFactory(GsonConverterFactory.create())
            .client(http)
            .build()
            .create(ApiService::class.java)
    }
}
