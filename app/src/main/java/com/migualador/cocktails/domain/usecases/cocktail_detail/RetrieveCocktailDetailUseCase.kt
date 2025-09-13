package com.migualador.cocktails.domain.usecases.cocktail_detail

import com.migualador.cocktails.data.entities.CocktailDetail
import com.migualador.cocktails.data.repositories.CocktailsDetailsRepository
import com.migualador.cocktails.domain.BaseUseCase
import javax.inject.Inject

/**
 * RetrieveCocktailDetailUseCase
 *
 * Retrieves the detail of a cocktail
 *
 */
class RetrieveCocktailDetailUseCase @Inject constructor (
    private val cocktailsDetailsRepository: CocktailsDetailsRepository,
): BaseUseCase<String, RetrieveCocktailDetailUseCase.UseCaseResult>() {

    sealed class UseCaseResult {
        data class Success(val data: CocktailDetail): UseCaseResult()
        data object CocktailNotFoundError: UseCaseResult()
        data object NetworkError: UseCaseResult()
    }

    override suspend fun useCaseContent(params: String): UseCaseResult {
        return try {
            val result = cocktailsDetailsRepository.getCocktailDetail(params)
            if (result == null) {
                UseCaseResult.CocktailNotFoundError
            } else {
                UseCaseResult.Success(result)
            }

        } catch (_: Exception) {
            UseCaseResult.NetworkError
        }
    }
}