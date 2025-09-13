package com.migualador.cocktails.domain.usecases.alcoholic_cocktails

import com.migualador.cocktails.data.entities.Cocktail
import com.migualador.cocktails.data.repositories.AlcoholicCocktailsRepository
import com.migualador.cocktails.domain.BaseUseCase
import javax.inject.Inject

/**
 * RetrieveAlcoholicCocktailsUseCase
 *
 * Retrieves the list of alcoholic cocktails
 *
 */
class RetrieveAlcoholicCocktailsUseCase @Inject constructor (
    private val alcoholicCocktailsRepository: AlcoholicCocktailsRepository,
): BaseUseCase<Unit, RetrieveAlcoholicCocktailsUseCase.UseCaseResult>() {

    sealed class UseCaseResult {
        data class Success(val data: List<Cocktail>): UseCaseResult()
        data object NetworkError: UseCaseResult()
    }

    override suspend fun useCaseContent(params: Unit): UseCaseResult {
        return try {
            val result = alcoholicCocktailsRepository.getAlcoholicCocktails()
            UseCaseResult.Success(result)

        } catch (_: Exception) {
            UseCaseResult.NetworkError
        }
    }
}