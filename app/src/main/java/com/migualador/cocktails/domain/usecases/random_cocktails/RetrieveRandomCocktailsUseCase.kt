package com.migualador.cocktails.domain.usecases.random_cocktails

import com.migualador.cocktails.data.entities.CocktailDetail
import com.migualador.cocktails.data.repositories.AlcoholicCocktailsRepository
import com.migualador.cocktails.data.repositories.CocktailsDetailsRepository
import com.migualador.cocktails.domain.BaseUseCase
import javax.inject.Inject
import kotlin.random.Random

/**
 * RetrieveRandomCocktailsUseCase
 *
 * Retrieves a list containing the cocktail detail of 5 random alcoholic cocktails
 */
class RetrieveRandomCocktailsUseCase @Inject constructor (
    private val alcoholicCocktailsRepository: AlcoholicCocktailsRepository,
    private val cocktailsDetailsRepository: CocktailsDetailsRepository
): BaseUseCase<Unit, RetrieveRandomCocktailsUseCase.UseCaseResult>() {

    sealed class UseCaseResult {
        data class Success(val data: List<CocktailDetail>): UseCaseResult()
        data object NetworkError: UseCaseResult()
    }

    override suspend fun useCaseContent(params: Unit): UseCaseResult {
        return try {
            val alcoholicCocktails = alcoholicCocktailsRepository.getAlcoholicCocktails()
            val result = ArrayList<CocktailDetail>()

            repeat(5) {
                val index = Random.nextInt(alcoholicCocktails.size)
                val cocktailDetail = cocktailsDetailsRepository.getCocktailDetail(alcoholicCocktails[index].id)
                if (cocktailDetail != null) {
                    result.add(cocktailDetail)
                }
            }

            UseCaseResult.Success(result)

        } catch (_: Exception) {
            UseCaseResult.NetworkError
        }
    }
}