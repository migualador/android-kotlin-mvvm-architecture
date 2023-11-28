package com.migualador.cocktails.data.datasources.cocktail_detail

import com.migualador.cocktails.data.entities.CocktailDetail
import javax.inject.Inject
import javax.inject.Singleton

/**
 * CocktailsDetailsLocalDataSource
 *
 * Data source that holds cocktails details data in memory
 */
@Singleton
class CocktailsDetailsLocalDataSource @Inject constructor() {

    private var cocktailsDetails = ArrayList<CocktailDetail>()

    fun getCocktailDetail(cocktailId: String): CocktailDetail? {
        return cocktailsDetails.find { it.id == cocktailId}
    }

    fun addCocktailDetail(cocktailDetail: CocktailDetail) {
        if (cocktailsDetails.find {it.id == cocktailDetail.id} == null ) {
            cocktailsDetails.add(cocktailDetail)
        }
    }

}