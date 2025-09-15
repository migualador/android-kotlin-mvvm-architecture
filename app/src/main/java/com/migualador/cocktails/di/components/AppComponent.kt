package com.migualador.cocktails.di.components

import com.migualador.cocktails.di.modules.AppModule
import com.migualador.cocktails.di.modules.NetworkModule
import com.migualador.cocktails.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NetworkModule::class,
])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun getViewModelComponent(): ViewModelsComponent.Factory
}