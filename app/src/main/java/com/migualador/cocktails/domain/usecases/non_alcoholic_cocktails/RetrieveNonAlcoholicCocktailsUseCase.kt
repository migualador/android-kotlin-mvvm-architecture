package com.migualador.cocktails.domain.usecases.non_alcoholic_cocktails

import com.migualador.cocktails.data.entities.Cocktail
import com.migualador.cocktails.data.repositories.NonAlcoholicCocktailsRepository
import com.migualador.cocktails.domain.BaseUseCase
import com.migualador.cocktails.domain.UseCaseResult
import javax.inject.Inject

/**
 * RetrieveNonAlcoholicCocktailsUseCase
 *
 * Retrieves the list of non alcoholic cocktails
 */
class RetrieveNonAlcoholicCocktailsUseCase @Inject constructor (
    private val nonAlcoholicCocktailsRepository: NonAlcoholicCocktailsRepository,
): BaseUseCase<Unit, List<Cocktail>>() {

    override suspend fun useCaseContent(params: Unit): UseCaseResult<List<Cocktail>> {
        return try {
            val result = nonAlcoholicCocktailsRepository.getNonAlcoholicCocktails()
            if (result == null) {
                UseCaseResult.Error(IllegalStateException("The non alcoholic cocktails list is empty"))
            } else {
                UseCaseResult.Success(result)
            }
        } catch (e: Exception) {
            UseCaseResult.Error(e)
        }
    }
}