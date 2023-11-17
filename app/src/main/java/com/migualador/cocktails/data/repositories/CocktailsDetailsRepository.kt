package com.migualador.cocktails.data.repositories

import com.migualador.cocktails.data.datasources.cocktail_detail.CocktailsDetailsRemoteDataSource
import com.migualador.cocktails.data.entities.CocktailDetail
import javax.inject.Inject

/**
 * CocktailsDetailsRepository
 *
 * Holds cocktails detailed information
 */
class CocktailsDetailsRepository @Inject constructor (
    private val cocktailsDetailsRemoteDataSource: CocktailsDetailsRemoteDataSource
) {

   suspend fun getCocktailDetail(cocktailId: String): CocktailDetail? {
        return cocktailsDetailsRemoteDataSource.getCocktailDetail(cocktailId)
    }
}