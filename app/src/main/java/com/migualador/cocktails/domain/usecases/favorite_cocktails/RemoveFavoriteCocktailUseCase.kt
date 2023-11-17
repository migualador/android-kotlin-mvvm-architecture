package com.migualador.cocktails.domain.usecases.favorite_cocktails

import com.migualador.cocktails.data.entities.FavoriteCocktail
import com.migualador.cocktails.data.repositories.FavoriteCocktailsRepository
import com.migualador.cocktails.domain.BaseUseCase
import com.migualador.cocktails.domain.UseCaseResult
import javax.inject.Inject

/**
 * RemoveFavoriteCocktailUseCase
 *
 * Removes a cocktail from the favorites list
 */
class RemoveFavoriteCocktailUseCase @Inject constructor (
    private val favoriteCocktailsRepository: FavoriteCocktailsRepository
): BaseUseCase<String, Unit>() {

    override suspend fun useCaseContent(params: String): UseCaseResult<Unit> {
        val favoriteCocktail = FavoriteCocktail(params)
        favoriteCocktailsRepository.removeFavoriteCocktail(favoriteCocktail)
        return UseCaseResult.Success(Unit)
    }

}