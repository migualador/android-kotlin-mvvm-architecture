package com.migualador.cocktails.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.migualador.cocktails.domain.UseCaseResult
import com.migualador.cocktails.domain.usecases.alcoholic_cocktails.RetrieveAlcoholicCocktailsUseCase
import com.migualador.cocktails.domain.usecases.favorite_cocktails.RetrieveFavoriteCocktailsUseCase
import com.migualador.cocktails.domain.usecases.non_alcoholic_cocktails.RetrieveNonAlcoholicCocktailsUseCase
import com.migualador.cocktails.domain.usecases.random_cocktails.RetrieveRandomCocktailsUseCase
import com.migualador.cocktails.presentation.Event
import com.migualador.cocktails.presentation.home.mapper_extensions.toCocktailDetailUiState
import com.migualador.cocktails.presentation.home.mapper_extensions.toCocktailUiState
import com.migualador.cocktails.presentation.home.ui_states.CocktailDetailUiState
import com.migualador.cocktails.presentation.home.ui_states.CocktailUiState
import com.migualador.cocktails.presentation.home.ui_states.LoadingUiState
import com.migualador.cocktails.presentation.home.ui_states.NavigateUiState
import javax.inject.Inject

/**
 * HomeViewModel
 *
 */
class HomeViewModel @Inject constructor (
    private val retrieveFavoriteCocktailsUseCase: RetrieveFavoriteCocktailsUseCase,
    private val retrieveAlcoholicCocktailsUseCase: RetrieveAlcoholicCocktailsUseCase,
    private val retrieveNonAlcoholicCocktailsUseCase: RetrieveNonAlcoholicCocktailsUseCase,
    private val retrieveRandomCocktailsUseCase: RetrieveRandomCocktailsUseCase
): ViewModel() {

    private var _loadingUiStateLiveData = MutableLiveData<Event<LoadingUiState>>()
    val loadingUiStateLiveData: LiveData<Event<LoadingUiState>> = _loadingUiStateLiveData

    private val _navigateLiveData = MutableLiveData<Event<NavigateUiState>>()
    val navigateLiveData: LiveData<Event<NavigateUiState>> = _navigateLiveData

    private val _alcoholicCocktailsLiveData = MutableLiveData<List<CocktailUiState>>()
    val alcoholicCocktailsLiveData: LiveData<List<CocktailUiState>> = _alcoholicCocktailsLiveData

    private val _nonAlcoholicCocktailsLiveData = MutableLiveData<List<CocktailUiState>>()
    val nonAlcoholicCocktailsLiveData: LiveData<List<CocktailUiState>> = _nonAlcoholicCocktailsLiveData

    private val _favoriteCocktailsLiveData = MutableLiveData<List<CocktailUiState>>()
    val favoriteCocktailsLiveData: LiveData<List<CocktailUiState>> = _favoriteCocktailsLiveData

    private val _featuredCocktailsLiveData = MutableLiveData<List<CocktailDetailUiState>>()
    val featuredCocktailsLiveData: LiveData<List<CocktailDetailUiState>> = _featuredCocktailsLiveData

    private var stateAlreadyRetrieved = false

    fun requestUiState() {
        if (stateAlreadyRetrieved) {
            _loadingUiStateLiveData.value = Event(LoadingUiState(false))
            requestFavoriteCocktails()
        } else {
            requestAlcoholicCocktails()
        }
    }

    private fun requestAlcoholicCocktails() {

        _loadingUiStateLiveData.value = Event(LoadingUiState(true))

        retrieveAlcoholicCocktailsUseCase.execute(Unit) { result ->
            if (result is UseCaseResult.Success) {
                _alcoholicCocktailsLiveData.value = result.data.map {
                    it.toCocktailUiState(
                        onClick = { navigateToDetail(it.id) }
                    )
                }
            }

            requestNonAlcoholicCocktails()
        }

    }

    private fun requestNonAlcoholicCocktails() {

        retrieveNonAlcoholicCocktailsUseCase.execute(Unit) { result ->
            if (result is UseCaseResult.Success) {
                _nonAlcoholicCocktailsLiveData.value = result.data.map {
                    it.toCocktailUiState(
                        onClick = { navigateToDetail(it.id) }
                    )
                }
            }

            requestFeaturedCocktails()
        }

    }

    private fun requestFeaturedCocktails() {

        retrieveRandomCocktailsUseCase.execute(Unit) { result ->
            if (result is UseCaseResult.Success) {
                _featuredCocktailsLiveData.value = result.data.map {
                    it.toCocktailDetailUiState(
                        onClick = { navigateToDetail(it.id) }
                    )
                }
            }

            requestFavoriteCocktails()
        }

    }

    private fun requestFavoriteCocktails() {

        retrieveFavoriteCocktailsUseCase.execute(Unit) { result ->
            if (result is UseCaseResult.Success) {
                _favoriteCocktailsLiveData.value = result.data.map {
                    it.toCocktailUiState(
                        onClick = { navigateToDetail(it.id) }
                    )
                }
            }
            _loadingUiStateLiveData.value = Event(LoadingUiState(false))
        }

        stateAlreadyRetrieved = true
    }

    fun alcoholicCocktailsHeaderPressed() {
        _navigateLiveData.value = Event(NavigateUiState.NavigateToAlcoholicCocktailsList)
    }

    fun nonAlcoholicCocktailsHeaderPressed() {
        _navigateLiveData.value = Event(NavigateUiState.NavigateToNonAlcoholicCocktailsList)
    }

    fun favoriteCocktailsHeaderPressed() {
        _navigateLiveData.value = Event(NavigateUiState.NavigateToFavoriteCocktailsList)
    }

    private fun navigateToDetail(cocktailId: String) {
        _navigateLiveData.value = Event(NavigateUiState.NavigateToDetail(cocktailId))
    }

}