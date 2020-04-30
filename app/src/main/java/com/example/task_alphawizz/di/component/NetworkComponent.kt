package com.example.task_alphawizz.di.component

import android.content.Context
import com.example.task_alphawizz.api.NetworkHelper
import com.example.task_alphawizz.di.module.DataModule
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface NetworkComponent {

    fun getRetrofit() : Retrofit
    fun inject(networkHelper: NetworkHelper)



    @Component.Factory
    interface Factory {
        fun create(@BindsInstance @Named("name") name: String,@BindsInstance @Named("appContext") context: Context): NetworkComponent
    }

}