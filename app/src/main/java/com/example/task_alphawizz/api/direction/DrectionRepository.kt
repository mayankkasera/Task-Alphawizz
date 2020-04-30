package com.example.task_alphawizz.api.direction

import android.util.Log
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrectionRepository(private val directionRequests: DirectionRequests) : DrectionRepositoryI {

    override fun getDrection(url: String): Observable<String> {
        return Observable.create<String> { emitter ->
            directionRequests.getDirection(url).enqueue(object : Callback<String> {
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