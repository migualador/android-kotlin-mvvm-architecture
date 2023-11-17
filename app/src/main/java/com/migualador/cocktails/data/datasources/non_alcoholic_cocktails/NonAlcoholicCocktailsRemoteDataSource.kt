package com.migualador.cocktails.data.datasources.non_alcoholic_cocktails

import com.migualador.cocktails.Constants
import com.migualador.cocktails.data.entities.Cocktail
import com.migualador.cocktails.data.network.api.CocktailsAPI
import com.migualador.cocktails.data.network.mapper_extensions.toCocktail
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * NonAlcoholicCocktailsRemoteDataSource
 *
 * Retrieves non alcoholic cocktails data from REST API
 */
class NonAlcoholicCocktailsRemoteDataSource @Inject constructor() {

    suspend fun getNonAlcoholicCocktails(): List<Cocktail>? {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.THE_COCKTAIL_DB_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val cocktailsAPI = retrofit.create(CocktailsAPI::class.java)
        val response = cocktailsAPI.getNonAlcoholicCocktails(Constants.THE_COCKTAIL_DB_API_KEY).awaitResponse()
        val getNonAlcoholicCocktailsResponse = response.body()
        return getNonAlcoholicCocktailsResponse?.drinks?.map { it.toCocktail() }
    }

}