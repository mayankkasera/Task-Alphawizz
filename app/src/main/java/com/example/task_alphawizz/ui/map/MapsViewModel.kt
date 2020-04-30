package com.example.task_alphawizz.ui.map


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task_alphawizz.api.direction.DrectionRepositoryI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MapsViewModel(var directionRepositoryI: DrectionRepositoryI) : ViewModel() {

    var mutableLiveData: MutableLiveData<MapsState> = MutableLiveData()
    private var compositeDisposable = CompositeDisposable()

    fun getDirection(url : String) {
        compositeDisposable.add(
            directionRepositoryI.getDrection(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    publishState(MapsState.Succes(it))
                },{
                    publishState(MapsState.Failure(it.message!!))
                },{

                },{

                })
        )
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    private fun publishState(state: MapsState) {
        mutableLiveData.postValue(state)
    }
}