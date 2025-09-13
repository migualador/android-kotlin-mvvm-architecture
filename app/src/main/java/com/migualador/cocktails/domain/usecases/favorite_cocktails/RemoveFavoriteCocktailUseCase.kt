package com.migualador.cocktails.domain.usecases.favorite_cocktails

import com.migualador.cocktails.data.entities.FavoriteCocktail
import com.migualador.cocktails.data.repositories.FavoriteCocktailsRepository
import com.migualador.cocktails.domain.BaseUseCase
import javax.inject.Inject

/**
 * RemoveFavoriteCocktailUseCase
 *
 * Removes a cocktail from the favorites list
 */
class RemoveFavoriteCocktailUseCase @Inject constructor (
    private val favoriteCocktailsRepository: FavoriteCocktailsRepository
): BaseUseCase<String, Unit>() {

    override suspend fun useCaseContent(params: String) {
        val favoriteCocktail = FavoriteCocktail(params)
        favoriteCocktailsRepository.removeFavoriteCocktail(favoriteCocktail)
    }

}