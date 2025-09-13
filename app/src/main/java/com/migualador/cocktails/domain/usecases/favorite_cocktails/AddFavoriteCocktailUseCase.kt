package com.migualador.cocktails.domain.usecases.favorite_cocktails

import com.migualador.cocktails.data.entities.FavoriteCocktail
import com.migualador.cocktails.data.repositories.FavoriteCocktailsRepository
import com.migualador.cocktails.domain.BaseUseCase
import javax.inject.Inject

/**
 * AddFavoriteCocktailUseCase
 *
 * Add a cocktail to the favorites list
 */
class AddFavoriteCocktailUseCase @Inject constructor (
    private val favoriteCocktailsRepository: FavoriteCocktailsRepository
): BaseUseCase<String, Unit>() {

    override suspend fun useCaseContent(params: String) {
        val favoriteCocktail = FavoriteCocktail(params)
        favoriteCocktailsRepository.addFavoriteCocktail(favoriteCocktail)
    }

}