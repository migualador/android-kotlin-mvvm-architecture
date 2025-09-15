package com.migualador.cocktails.data.datasources.cocktail_detail

import com.migualador.cocktails.Constants
import com.migualador.cocktails.data.entities.CocktailDetail
import com.migualador.cocktails.data.network.NetworkResult
import com.migualador.cocktails.data.network.api.CocktailsAPI
import com.migualador.cocktails.data.network.mapper_extensions.toCocktailDetail
import retrofit2.awaitResponse
import javax.inject.Inject

/**
 * AlcoholicCocktailsRemoteDataSource
 *
 * Retrieves cocktails details from REST API
 */
class CocktailsDetailsRemoteDataSource @Inject constructor(
    val cocktailsAPI: CocktailsAPI
) {

    suspend fun getCocktailDetail(cocktailId: String): NetworkResult<CocktailDetail?> {
        return try {

            val response = cocktailsAPI.getCocktailDetail(
                Constants.THE_COCKTAIL_DB_API_KEY,
                cocktailId
            ).awaitResponse()

            if (response.isSuccessful) {
                val getCocktailDetailResponse = response.body()
                val drink = getCocktailDetailResponse?.drinks?.get(0)
                NetworkResult.Success(drink?.toCocktailDetail())
            } else {
                NetworkResult.HttpError(response.code())
            }

        } catch (exception: Exception) {
            NetworkResult.ConnectionError()
        }
    }

}