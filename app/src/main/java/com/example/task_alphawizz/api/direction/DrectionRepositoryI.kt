package com.example.task_alphawizz.api.direction

import io.reactivex.Observable

interface DrectionRepositoryI {

    fun getDrection(url: String) : Observable<String>

}