package com.migualador.cocktails.data.datasources.alcoholic_cocktails

import com.migualador.cocktails.Constants
import com.migualador.cocktails.data.entities.Cocktail
import com.migualador.cocktails.data.network.NetworkResult
import com.migualador.cocktails.data.network.api.CocktailsAPI
import com.migualador.cocktails.data.network.mapper_extensions.toCocktail
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * AlcoholicCocktailsRemoteDataSource
 *
 * Retrieves alcoholic cocktails data from REST API
 */
class AlcoholicCocktailsRemoteDataSource @Inject constructor() {

    suspend fun getAlcoholicCocktails(): NetworkResult<List<Cocktail>?> {
        return try {

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.THE_COCKTAIL_DB_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val cocktailsAPI = retrofit.create(CocktailsAPI::class.java)

            val response = cocktailsAPI.getAlcoholicCocktails(Constants.THE_COCKTAIL_DB_API_KEY)
                .awaitResponse()

            if (response.isSuccessful) {
                val getAlcoholicCocktailsResponse = response.body()
                val alcoholicCocktails = getAlcoholicCocktailsResponse?.drinks?.map { it.toCocktail() }
                NetworkResult.Success(alcoholicCocktails)
            } else {
                NetworkResult.HttpError(response.code())
            }

        } catch (exception: Exception) {
            NetworkResult.ConnectionError()
        }

    }

}