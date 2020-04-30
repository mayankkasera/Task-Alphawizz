package com.example.task_alphawizz.api.direction

import io.reactivex.Observable

interface DrectionRepositoryI {


    fun getDrection(
        mode: String,
        transit_routing_preference: String,
        origin: String,
        destination: String,
        key: String
    ) : Observable<String>

}
