package com.migualador.cocktails.di.modules

import android.app.Application
import android.content.Context
import com.migualador.cocktails.di.components.ViewModelsComponent
import com.migualador.cocktails.other.logging.Logger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [ViewModelsComponent::class])
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideContext() = app as Context

    @Provides
    @Singleton
    fun provideLogger() = Logger(app)

}