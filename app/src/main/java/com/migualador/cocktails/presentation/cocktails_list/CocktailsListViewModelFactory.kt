package com.migualador.cocktails.presentation.cocktails_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.migualador.cocktails.domain.usecases.alcoholic_cocktails.RetrieveAlcoholicCocktailsUseCase
import com.migualador.cocktails.domain.usecases.favorite_cocktails.RetrieveFavoriteCocktailsUseCase
import com.migualador.cocktails.domain.usecases.non_alcoholic_cocktails.RetrieveNonAlcoholicCocktailsUseCase

class CocktailsListViewModelFactory(
    private val retrieveAlcoholicCocktailsUseCase: RetrieveAlcoholicCocktailsUseCase,
    private val retrieveNonAlcoholicCocktailsUseCase: RetrieveNonAlcoholicCocktailsUseCase,
    private val retrieveFavoriteCocktailsUseCase: RetrieveFavoriteCocktailsUseCase
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CocktailsListViewModel::class.java)) {
            return CocktailsListViewModel(
                retrieveAlcoholicCocktailsUseCase,
                retrieveNonAlcoholicCocktailsUseCase,
                retrieveFavoriteCocktailsUseCase
            ) as T
        } else throw IllegalArgumentException("Unknown ViewModel class")
    }
}