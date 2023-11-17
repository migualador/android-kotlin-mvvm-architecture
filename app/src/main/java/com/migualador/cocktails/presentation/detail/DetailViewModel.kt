package com.migualador.cocktails.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.migualador.cocktails.domain.UseCaseResult
import com.migualador.cocktails.domain.usecases.cocktail_detail.RetrieveCocktailDetailUseCase
import com.migualador.cocktails.domain.usecases.favorite_cocktails.AddFavoriteCocktailUseCase
import com.migualador.cocktails.domain.usecases.favorite_cocktails.IsCocktailInFavoriteCocktailsUseCase
import com.migualador.cocktails.domain.usecases.favorite_cocktails.RemoveFavoriteCocktailUseCase
import com.migualador.cocktails.presentation.Event
import com.migualador.cocktails.presentation.home.mapper_extensions.toCocktailDetailUiState
import com.migualador.cocktails.presentation.home.ui_states.CocktailDetailUiState
import com.migualador.cocktails.presentation.home.ui_states.NavigateBackUiState


/**
 * DetailViewModel
 *
 */
class DetailViewModel(
    private val isCocktailInFavoriteCocktailsUseCase: IsCocktailInFavoriteCocktailsUseCase,
    private val addFavoriteCocktailUseCase: AddFavoriteCocktailUseCase,
    private val removeFavoriteCocktailUseCase: RemoveFavoriteCocktailUseCase,
    private val retrieveCocktailDetailUseCase: RetrieveCocktailDetailUseCase
): ViewModel() {

    private val _isFavoriteLiveData = MutableLiveData<Boolean>()
    val isFavoriteLiveData: LiveData<Boolean> = _isFavoriteLiveData

    private val _navigateBackLiveData = MutableLiveData<Event<NavigateBackUiState>>()
    val navigateBackLiveData : LiveData<Event<NavigateBackUiState>> = _navigateBackLiveData

    private val _cocktailDetailLiveData = MutableLiveData<CocktailDetailUiState>()
    val cocktailDetailLiveData: LiveData<CocktailDetailUiState> = _cocktailDetailLiveData

    private var currentCocktailId = ""

    private var isFavorite = false

    fun obtainCocktailDetail(cocktailId: String) {

        retrieveCocktailDetailUseCase.execute(cocktailId) { result ->
            currentCocktailId = cocktailId
            if (result is UseCaseResult.Success) {
                _cocktailDetailLiveData.value = result.data.toCocktailDetailUiState()

            }
        }

        isCocktailInFavoriteCocktailsUseCase.execute(cocktailId) { result ->
            if (result is UseCaseResult.Success) {
                isFavorite = result.data
                _isFavoriteLiveData.value = isFavorite
            }
        }

    }

    fun toggleFavorite() {
        isFavorite = !isFavorite

        _isFavoriteLiveData.value = isFavorite

        if (isFavorite) {
            addFavoriteCocktailUseCase.execute(currentCocktailId)
        } else {
            removeFavoriteCocktailUseCase.execute(currentCocktailId)
        }
    }

    fun backButtonPressed() {
        _navigateBackLiveData.value = Event(NavigateBackUiState())
    }
}