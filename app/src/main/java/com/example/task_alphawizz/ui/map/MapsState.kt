package com.example.task_alphawizz.ui.map

sealed class MapsState{

    data class Succes(var responce : String) : MapsState()
    data class Failure(var message : String) : MapsState()


}