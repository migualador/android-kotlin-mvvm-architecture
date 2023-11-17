package com.migualador.cocktails.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.migualador.cocktails.domain.usecases.alcoholic_cocktails.RetrieveAlcoholicCocktailsUseCase
import com.migualador.cocktails.domain.usecases.non_alcoholic_cocktails.RetrieveNonAlcoholicCocktailsUseCase
import com.migualador.cocktails.domain.usecases.random_cocktails.RetrieveRandomCocktailsUseCase
import com.migualador.cocktails.domain.usecases.favorite_cocktails.RetrieveFavoriteCocktailsUseCase

class HomeViewModelFactory(
    private val retrieveFavoriteCocktailsUseCase: RetrieveFavoriteCocktailsUseCase,
    private val retrieveAlcoholicCocktailsUseCase: RetrieveAlcoholicCocktailsUseCase,
    private val retrieveNonAlcoholicCocktailsUseCase: RetrieveNonAlcoholicCocktailsUseCase,
    private val retrieveRandomCocktailsUseCase: RetrieveRandomCocktailsUseCase
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                retrieveFavoriteCocktailsUseCase,
                retrieveAlcoholicCocktailsUseCase,
                retrieveNonAlcoholicCocktailsUseCase,
                retrieveRandomCocktailsUseCase
            ) as T
        }
        else throw IllegalArgumentException("Unknown ViewModel class")
    }
}