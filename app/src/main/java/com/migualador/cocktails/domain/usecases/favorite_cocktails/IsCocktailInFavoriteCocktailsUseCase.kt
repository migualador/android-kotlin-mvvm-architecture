package com.migualador.cocktails.domain.usecases.favorite_cocktails

import com.migualador.cocktails.data.repositories.FavoriteCocktailsRepository
import com.migualador.cocktails.domain.BaseUseCase
import javax.inject.Inject

/**
 * IsCocktailInFavoriteCocktailsUseCase
 *
 * Checks whether a cocktail is in the favorites list or not
 */
class IsCocktailInFavoriteCocktailsUseCase @Inject constructor (
    private val favoriteCocktailsRepository: FavoriteCocktailsRepository
): BaseUseCase<String, Boolean>() {

    override suspend fun useCaseContent(params: String): Boolean {
        val cocktail = favoriteCocktailsRepository.getFavoriteCocktailById(params)
        return cocktail != null
    }
}