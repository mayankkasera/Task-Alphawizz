package com.example.task_alphawizz.api


import com.example.task_alphawizz.utils.App
import retrofit2.Retrofit
import javax.inject.Inject

class NetworkHelper {

    @Inject
    lateinit var retrofit: Retrofit


    init {
        App.networkComponent()?.inject(this)
    }



}