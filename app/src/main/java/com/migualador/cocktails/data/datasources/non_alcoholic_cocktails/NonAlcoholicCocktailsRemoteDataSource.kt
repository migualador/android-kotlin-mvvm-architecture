package com.migualador.cocktails.data.datasources.non_alcoholic_cocktails

import com.migualador.cocktails.Constants
import com.migualador.cocktails.data.entities.Cocktail
import com.migualador.cocktails.data.network.NetworkResult
import com.migualador.cocktails.data.network.api.CocktailsAPI
import com.migualador.cocktails.data.network.mapper_extensions.toCocktail
import retrofit2.awaitResponse
import javax.inject.Inject

/**
 * NonAlcoholicCocktailsRemoteDataSource
 *
 * Retrieves non alcoholic cocktails data from REST API
 */
class NonAlcoholicCocktailsRemoteDataSource @Inject constructor(
    val cocktailsAPI: CocktailsAPI
) {

    suspend fun getNonAlcoholicCocktails(): NetworkResult<List<Cocktail>?> {
        return try {

        val response = cocktailsAPI.getNonAlcoholicCocktails(
                url = Constants.environment.getNonAlcoholicCocktailsListUrl()
            ).awaitResponse()

        if (response.isSuccessful) {
            val getNonAlcoholicCocktailsResponse = response.body()
            val nonAlcoholicCocktails = getNonAlcoholicCocktailsResponse?.drinks?.map { it.toCocktail() }
            NetworkResult.Success(nonAlcoholicCocktails)
        } else {
            NetworkResult.HttpError(response.code())
        }

    } catch (exception: Exception) {
        NetworkResult.ConnectionError()
    }

    }

}