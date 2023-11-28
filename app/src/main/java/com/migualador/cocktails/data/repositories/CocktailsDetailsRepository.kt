package com.migualador.cocktails.data.repositories

import com.migualador.cocktails.data.datasources.cocktail_detail.CocktailsDetailsDBDataSource
import com.migualador.cocktails.data.datasources.cocktail_detail.CocktailsDetailsLocalDataSource
import com.migualador.cocktails.data.datasources.cocktail_detail.CocktailsDetailsRemoteDataSource
import com.migualador.cocktails.data.entities.CocktailDetail
import com.migualador.cocktails.data.network.NetworkResult
import com.migualador.cocktails.other.logging.Logger
import javax.inject.Inject

/**
 * CocktailsDetailsRepository
 *
 * Repository with cocktails details data. Uses three levels of data sources:
 * -Memory data source
 * -Network data source
 * -Room data source
 */
class CocktailsDetailsRepository @Inject constructor (
    private val cocktailsDetailsLocalDataSource: CocktailsDetailsLocalDataSource,
    private val cocktailsDetailsRemoteDataSource: CocktailsDetailsRemoteDataSource,
    private val cocktailsDetailsDBDataSource: CocktailsDetailsDBDataSource,
    private val logger: Logger
) {

    suspend fun getCocktailDetail(cocktailId: String): CocktailDetail? {
        var cocktailDetail = cocktailsDetailsLocalDataSource.getCocktailDetail(cocktailId)
        log("CocktailsDetailsLocalDataSource", cocktailDetail)

        if (cocktailDetail == null) {
            val networkResult = cocktailsDetailsRemoteDataSource.getCocktailDetail(cocktailId)
            cocktailDetail = when (networkResult) {
                is NetworkResult.Success -> {
                    networkResult.data?.let {
                        cocktailsDetailsLocalDataSource.addCocktailDetail(it)
                        cocktailsDetailsDBDataSource.addCocktailDetail(it)
                        it
                    }

                }
                is NetworkResult.HttpError -> null
                is NetworkResult.ConnectionError -> null
            }

            log("CocktailsDetailsRemoteDataSource", cocktailDetail)
        }

        if (cocktailDetail == null) {
            cocktailDetail = cocktailsDetailsDBDataSource.getCocktailDetail(cocktailId)
            log("CocktailsDetailsDBDataSource", cocktailDetail)
        }

        return cocktailDetail
    }

    private fun log(datasourceName: String, cocktailDetail: CocktailDetail?) {
        val count = if (cocktailDetail == null) 0
        else 1
        logger.log("Retrieving from ${datasourceName}: ${count} cocktail detail")
    }
}