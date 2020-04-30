package com.example.task_alphawizz.api.direction

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface DirectionRequests {
    @GET("maps/api/directions/json")
    fun getDirection(
        @Query("mode") mode: String,
        @Query("transit_routing_preference") transit_routing_preference: String,
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String
    ): Call<String>
}