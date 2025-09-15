package com.migualador.cocktails.data.datasources.alcoholic_cocktails

import com.migualador.cocktails.Constants
import com.migualador.cocktails.data.entities.Cocktail
import com.migualador.cocktails.data.network.NetworkResult
import com.migualador.cocktails.data.network.api.CocktailsAPI
import com.migualador.cocktails.data.network.mapper_extensions.toCocktail
import retrofit2.awaitResponse
import javax.inject.Inject

/**
 * AlcoholicCocktailsRemoteDataSource
 *
 * Retrieves alcoholic cocktails data from REST API
 */
class AlcoholicCocktailsRemoteDataSource @Inject constructor(
    val cocktailsAPI: CocktailsAPI
) {

    suspend fun getAlcoholicCocktails(): NetworkResult<List<Cocktail>?> {
        return try {

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