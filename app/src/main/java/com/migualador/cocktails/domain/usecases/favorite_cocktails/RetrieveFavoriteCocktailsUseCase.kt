package com.migualador.cocktails.domain.usecases.favorite_cocktails

import com.migualador.cocktails.data.entities.Cocktail
import com.migualador.cocktails.data.repositories.AlcoholicCocktailsRepository
import com.migualador.cocktails.data.repositories.FavoriteCocktailsRepository
import com.migualador.cocktails.data.repositories.NonAlcoholicCocktailsRepository
import com.migualador.cocktails.domain.BaseUseCase
import javax.inject.Inject

/**
 * RetrieveFavoriteCocktailsUseCase
 *
 * Retrieves favorites cocktails list
 */
class RetrieveFavoriteCocktailsUseCase @Inject constructor (
    private val favoriteCocktailsRepository: FavoriteCocktailsRepository,
    private val alcoholicCocktailsRepository: AlcoholicCocktailsRepository,
    private val nonAlcoholicCocktailsRepository: NonAlcoholicCocktailsRepository
): BaseUseCase<Unit, RetrieveFavoriteCocktailsUseCase.UseCaseResult>() {

    sealed class UseCaseResult {
        data class Success(val data: List<Cocktail>): UseCaseResult()
    }

    override suspend fun useCaseContent(params: Unit): UseCaseResult {
        val favoriteCocktails = favoriteCocktailsRepository.getFavoriteCocktails()
        val favoriteCocktailsIds = favoriteCocktails.map { it.cocktailId }
        val favoriteAlcoholicCocktails = alcoholicCocktailsRepository.getAlcoholicCocktailsById(favoriteCocktailsIds)
        val favoriteNonAlcoholicCocktails = nonAlcoholicCocktailsRepository.getNonAlcoholicCocktailsById(favoriteCocktailsIds)
        val cocktails = favoriteAlcoholicCocktails + favoriteNonAlcoholicCocktails

        return UseCaseResult.Success(cocktails)
    }
}