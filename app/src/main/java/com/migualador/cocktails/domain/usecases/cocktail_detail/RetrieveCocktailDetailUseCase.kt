package com.migualador.cocktails.domain.usecases.cocktail_detail

import com.migualador.cocktails.data.entities.CocktailDetail
import com.migualador.cocktails.data.repositories.CocktailsDetailsRepository
import com.migualador.cocktails.domain.BaseUseCase
import com.migualador.cocktails.domain.UseCaseResult
import javax.inject.Inject

/**
 * RetrieveCocktailDetailUseCase
 *
 * Retrieves the detail of a cocktail
 *
 */
class RetrieveCocktailDetailUseCase @Inject constructor (
    private val cocktailsDetailsRepository: CocktailsDetailsRepository,
): BaseUseCase<String, CocktailDetail>() {

    override suspend fun useCaseContent(params: String): UseCaseResult<CocktailDetail> {
        return try {
            val result = cocktailsDetailsRepository.getCocktailDetail(params)
            if (result == null) {
                UseCaseResult.Error(IllegalStateException("The cocktail detail couldn´t be retrieved"))
            } else {
                UseCaseResult.Success(result)
            }
        } catch (e: Exception) {
            UseCaseResult.Error(e)
        }
    }
}