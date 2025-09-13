package com.migualador.cocktails.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migualador.cocktails.domain.usecases.cocktail_detail.RetrieveCocktailDetailUseCase
import com.migualador.cocktails.domain.usecases.favorite_cocktails.AddFavoriteCocktailUseCase
import com.migualador.cocktails.domain.usecases.favorite_cocktails.IsCocktailInFavoriteCocktailsUseCase
import com.migualador.cocktails.domain.usecases.favorite_cocktails.RemoveFavoriteCocktailUseCase
import com.migualador.cocktails.presentation.Event
import com.migualador.cocktails.presentation.home.mapper_extensions.toCocktailDetailUiState
import com.migualador.cocktails.presentation.home.ui_states.CocktailDetailUiState
import com.migualador.cocktails.presentation.home.ui_states.NavigateBackUiState
import kotlinx.coroutines.launch


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

        viewModelScope.launch {
            when (val result = retrieveCocktailDetailUseCase(cocktailId)) {
                is RetrieveCocktailDetailUseCase.UseCaseResult.Success-> {
                    currentCocktailId = cocktailId
                    _cocktailDetailLiveData.value = result.data.toCocktailDetailUiState()

                }
                is RetrieveCocktailDetailUseCase.UseCaseResult.CocktailNotFoundError -> {}
                is RetrieveCocktailDetailUseCase.UseCaseResult.NetworkError -> {}
            }
        }

        viewModelScope.launch {
            val isFavorite = isCocktailInFavoriteCocktailsUseCase(cocktailId)
            _isFavoriteLiveData.value = isFavorite
        }
    }


    fun toggleFavorite() {
        isFavorite = !isFavorite

        _isFavoriteLiveData.value = isFavorite

        if (isFavorite) {
            viewModelScope.launch {
                addFavoriteCocktailUseCase(currentCocktailId)
            }
        } else {
            viewModelScope.launch {
                removeFavoriteCocktailUseCase(currentCocktailId)
            }
        }
    }

    fun backButtonPressed() {
        _navigateBackLiveData.value = Event(NavigateBackUiState())
    }
}