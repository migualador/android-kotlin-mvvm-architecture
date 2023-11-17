package com.migualador.cocktails

import android.app.Application
import com.migualador.cocktails.di.components.AppComponent
import com.migualador.cocktails.di.components.DaggerAppComponent
import com.migualador.cocktails.di.modules.AppModule

/**
 * CocktailsApp
 *
 */
class CocktailsApp: Application() {
    val appComponent: AppComponent = DaggerAppComponent
        .builder()
        .appModule(AppModule(this))
        .build()
}