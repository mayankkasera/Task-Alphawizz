package com.example.task_alphawizz.di.module


import com.example.task_alphawizz.api.direction.DirectionRequests
import com.example.task_alphawizz.api.direction.DrectionRepository
import com.example.task_alphawizz.api.direction.DrectionRepositoryI
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [NetworkModule::class]
)
class DataModule {

    @Provides
    @Singleton
    fun provideDrectionRepository(directionRequests: DirectionRequests) : DrectionRepositoryI {
        return DrectionRepository(directionRequests)
    }

}