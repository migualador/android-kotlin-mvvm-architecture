package com.migualador.cocktails.presentation.cocktails_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.migualador.cocktails.domain.UseCaseResult
import com.migualador.cocktails.domain.usecases.alcoholic_cocktails.RetrieveAlcoholicCocktailsUseCase
import com.migualador.cocktails.domain.usecases.favorite_cocktails.RetrieveFavoriteCocktailsUseCase
import com.migualador.cocktails.domain.usecases.non_alcoholic_cocktails.RetrieveNonAlcoholicCocktailsUseCase
import com.migualador.cocktails.presentation.Event
import com.migualador.cocktails.presentation.home.mapper_extensions.toCocktailUiState
import com.migualador.cocktails.presentation.home.ui_states.CocktailUiState
import com.migualador.cocktails.presentation.home.ui_states.NavigateBackUiState
import com.migualador.cocktails.presentation.home.ui_states.NavigateUiState

class CocktailsListViewModel(
    private val retrieveAlcoholicCocktailsUseCase: RetrieveAlcoholicCocktailsUseCase,
    private val retrieveNonAlcoholicCocktailsUseCase: RetrieveNonAlcoholicCocktailsUseCase,
    private val retrieveFavoriteCocktailsUseCase: RetrieveFavoriteCocktailsUseCase
): ViewModel() {

    private val _cocktailsLiveData = MutableLiveData<List<CocktailUiState>>()
    val cocktailsLiveData: LiveData<List<CocktailUiState>> = _cocktailsLiveData

    private val _navigateLiveData = MutableLiveData<Event<NavigateUiState>>()
    val navigateLiveData: LiveData<Event<NavigateUiState>> = _navigateLiveData

    private val _navigateBackLiveData = MutableLiveData<Event<NavigateBackUiState>>()
    val navigateBackLiveData : LiveData<Event<NavigateBackUiState>> = _navigateBackLiveData

    fun requestAlcoholicCocktails() {

        retrieveAlcoholicCocktailsUseCase.execute(Unit) { result ->
            if (result is UseCaseResult.Success) {
                _cocktailsLiveData.value = result.data.map {
                    it.toCocktailUiState(
                        onClick = { navigateToDetail(it.id) }
                    )
                }
            }
        }
    }

    fun requestNonAlcoholicCocktails() {

        retrieveNonAlcoholicCocktailsUseCase.execute(Unit) { result ->
            if (result is UseCaseResult.Success) {
                _cocktailsLiveData.value = result.data.map {
                    it.toCocktailUiState(
                        onClick = { navigateToDetail(it.id) }
                    )
                }
            }
        }

    }

    fun requestFavoriteCocktails() {

        retrieveFavoriteCocktailsUseCase.execute(Unit) { result ->
            if (result is UseCaseResult.Success) {
                _cocktailsLiveData.value = result.data.map {
                    it.toCocktailUiState(
                        onClick = { navigateToDetail(it.id) }
                    )
                }
            }
        }

    }

    fun backButtonPressed() {
        _navigateBackLiveData.value = Event(NavigateBackUiState())
    }

    private fun navigateToDetail(cocktail: String) {
        _navigateLiveData.value = Event(NavigateUiState.NavigateToDetail(cocktail))
    }
}