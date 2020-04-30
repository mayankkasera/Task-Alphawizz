package com.example.task_alphawizz.api.direction

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface DirectionRequests {
    @GET
    fun getDirection(@Url url: String): Call<String>
}