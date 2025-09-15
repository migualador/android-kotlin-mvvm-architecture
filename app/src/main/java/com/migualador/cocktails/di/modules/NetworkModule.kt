package com.migualador.cocktails.di.modules

import com.migualador.cocktails.Constants
import com.migualador.cocktails.data.network.api.CocktailsAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideCocktailsApi(): CocktailsAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.THE_COCKTAIL_DB_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CocktailsAPI::class.java)
    }
}