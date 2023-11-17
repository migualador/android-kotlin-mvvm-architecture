package com.migualador.cocktails.data.datasources.cocktail_detail

import com.migualador.cocktails.Constants
import com.migualador.cocktails.data.entities.CocktailDetail
import com.migualador.cocktails.data.network.api.CocktailsAPI
import com.migualador.cocktails.data.network.mapper_extensions.toCocktailDetail
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * AlcoholicCocktailsRemoteDataSource
 *
 * Retrieves cocktails detailsfrom REST API
 */
class CocktailsDetailsRemoteDataSource @Inject constructor() {

    suspend fun getCocktailDetail(cocktailId: String): CocktailDetail? {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.THE_COCKTAIL_DB_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val cocktailsAPI = retrofit.create(CocktailsAPI::class.java)
        val response = cocktailsAPI.getCocktailDetail(
            Constants.THE_COCKTAIL_DB_API_KEY,
            cocktailId
        ).awaitResponse()
        val getCocktailDetailResponse = response.body()
        val drink = getCocktailDetailResponse?.drinks?.get(0)
        return drink?.toCocktailDetail()
    }

}