package com.example.task_alphawizz.api.direction

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Url

interface DrectionRepositoryI {

    fun getDrection(url: String) : Observable<String>


}