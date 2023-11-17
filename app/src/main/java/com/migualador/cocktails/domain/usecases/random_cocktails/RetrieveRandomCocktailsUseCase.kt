package com.migualador.cocktails.domain.usecases.random_cocktails

import com.migualador.cocktails.data.entities.CocktailDetail
import com.migualador.cocktails.data.repositories.AlcoholicCocktailsRepository
import com.migualador.cocktails.data.repositories.CocktailsDetailsRepository
import com.migualador.cocktails.domain.BaseUseCase
import com.migualador.cocktails.domain.UseCaseResult
import javax.inject.Inject
import kotlin.random.Random

/**
 * RetrieveRandomCocktailsUseCase
 *
 * Retrieves a list containing the cocktail detail of 5 random cocktails
 */
class RetrieveRandomCocktailsUseCase @Inject constructor (
    private val alcoholicCocktailsRepository: AlcoholicCocktailsRepository,
    private val cocktailsDetailsRepository: CocktailsDetailsRepository
): BaseUseCase<Unit, List<CocktailDetail>>() {

    override suspend fun useCaseContent(params: Unit): UseCaseResult<List<CocktailDetail>> {
        return try {
            val alcoholicCocktails = alcoholicCocktailsRepository.getAlcoholicCocktails()
            val result = ArrayList<CocktailDetail>()
            if (alcoholicCocktails == null) {
                UseCaseResult.Error(IllegalStateException("The alcoholic cocktails list is empty"))
            } else {
                for(i in 0..4) {
                    val index = Random.nextInt(alcoholicCocktails.size)
                    val cocktailDetail = cocktailsDetailsRepository.getCocktailDetail(alcoholicCocktails[index].id)
                    if (cocktailDetail != null) {
                        result.add(cocktailDetail)
                    }
                }
                UseCaseResult.Success(result)
            }
        } catch (e: Exception) {
            UseCaseResult.Error(e)
        }
    }
}