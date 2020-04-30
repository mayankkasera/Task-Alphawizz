package com.example.task_alphawizz.api


import com.example.task_alphawizz.api.direction.DrectionRepositoryI
import com.example.task_alphawizz.utils.App
import retrofit2.Retrofit
import javax.inject.Inject

class DataHelper {


    init {
        App.networkComponent()?.inject(this)
    }

    @Inject
    lateinit var drectionRepositoryI: DrectionRepositoryI


}