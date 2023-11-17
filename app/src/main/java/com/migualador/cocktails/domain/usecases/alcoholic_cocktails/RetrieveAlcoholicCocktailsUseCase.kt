package com.migualador.cocktails.domain.usecases.alcoholic_cocktails

import com.migualador.cocktails.data.entities.Cocktail
import com.migualador.cocktails.data.repositories.AlcoholicCocktailsRepository
import com.migualador.cocktails.domain.BaseUseCase
import com.migualador.cocktails.domain.UseCaseResult
import javax.inject.Inject

/**
 * RetrieveAlcoholicCocktailsUseCase
 *
 * Retrieves the list of alcoholic cocktails
 *
 */
class RetrieveAlcoholicCocktailsUseCase @Inject constructor (
    private val alcoholicCocktailsRepository: AlcoholicCocktailsRepository,
): BaseUseCase<Unit, List<Cocktail>>() {

    override suspend fun useCaseContent(params: Unit): UseCaseResult<List<Cocktail>> {
        return try {
            val result = alcoholicCocktailsRepository.getAlcoholicCocktails()
            if (result == null) {
                UseCaseResult.Error(IllegalStateException("The alcoholic cocktails list is empty"))
            } else {
                UseCaseResult.Success(result)
            }
        } catch (e: Exception) {
            UseCaseResult.Error(e)
        }
    }
}