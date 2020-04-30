package com.example.task_alphawizz.api.direction

import android.util.Log
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class DrectionRepository(private val directionRequests: DirectionRequests) : DrectionRepositoryI {

    override fun getDrection(
        mode: String,
        transit_routing_preference: String,
        origin: String,
        destination: String,
        key: String
    ): Observable<String> {
        return Observable.create<String> { emitter ->
            directionRequests.getDirection(mode,transit_routing_preference,origin,destination,key).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.i("kdsjcn", "shdvcjds  : " + response.body().toString())
                    Log.i("kdsjcn", "shdvcjds  : " + response.toString())
                    response.body()?.let {
                        emitter.onNext(it)
                        emitter.onComplete()
                    } ?: run {
                        emitter.onNext(String())
                        emitter.onComplete()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i("kdsjcn", "" + t.toString())
                    emitter.onError(t)
                }
            })
        }
    }



}