package com.migualador.cocktails.domain.usecases.non_alcoholic_cocktails

import com.migualador.cocktails.data.entities.Cocktail
import com.migualador.cocktails.data.repositories.NonAlcoholicCocktailsRepository
import com.migualador.cocktails.domain.BaseUseCase
import javax.inject.Inject

/**
 * RetrieveNonAlcoholicCocktailsUseCase
 *
 * Retrieves the list of non alcoholic cocktails
 */
class RetrieveNonAlcoholicCocktailsUseCase @Inject constructor (
    private val nonAlcoholicCocktailsRepository: NonAlcoholicCocktailsRepository,
): BaseUseCase<Unit, RetrieveNonAlcoholicCocktailsUseCase.UseCaseResult>() {

    sealed class UseCaseResult {
        data class Success(val data: List<Cocktail>): UseCaseResult()
        data object NetworkError: UseCaseResult()
    }

    override suspend fun useCaseContent(params: Unit): UseCaseResult {
        return try {
            val result = nonAlcoholicCocktailsRepository.getNonAlcoholicCocktails()
            UseCaseResult.Success(result)

        } catch (_: Exception) {
            UseCaseResult.NetworkError
        }
    }
}