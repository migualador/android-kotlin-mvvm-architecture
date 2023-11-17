package com.migualador.cocktails.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.migualador.cocktails.domain.usecases.cocktail_detail.RetrieveCocktailDetailUseCase
import com.migualador.cocktails.domain.usecases.favorite_cocktails.AddFavoriteCocktailUseCase
import com.migualador.cocktails.domain.usecases.favorite_cocktails.IsCocktailInFavoriteCocktailsUseCase
import com.migualador.cocktails.domain.usecases.favorite_cocktails.RemoveFavoriteCocktailUseCase

class DetailViewModelFactory(
    private val isCocktailInFavoriteCocktailsUseCase: IsCocktailInFavoriteCocktailsUseCase,
    private val addFavoriteCocktailUseCase: AddFavoriteCocktailUseCase,
    private val removeFavoriteCocktailUseCase: RemoveFavoriteCocktailUseCase,
    private val retrieveCocktailDetailUseCase: RetrieveCocktailDetailUseCase
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(
                isCocktailInFavoriteCocktailsUseCase,
                addFavoriteCocktailUseCase,
                removeFavoriteCocktailUseCase,
                retrieveCocktailDetailUseCase
            ) as T
        } else throw IllegalArgumentException("Unknown ViewModel class")
    }
}