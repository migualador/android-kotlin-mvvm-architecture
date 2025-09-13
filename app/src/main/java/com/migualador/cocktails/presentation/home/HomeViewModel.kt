package com.migualador.cocktails.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.launch
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

    private var _loadingUiStateLiveData = MutableLiveData<LoadingUiState>()
    val loadingUiStateLiveData: LiveData<LoadingUiState> = _loadingUiStateLiveData

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

    init {
        requestAlcoholicCocktails()
    }

    private fun requestAlcoholicCocktails() {

        _loadingUiStateLiveData.value = LoadingUiState(true)

        viewModelScope.launch {
            when (val result = retrieveAlcoholicCocktailsUseCase(Unit)) {
                is RetrieveAlcoholicCocktailsUseCase.UseCaseResult.Success -> {
                    _alcoholicCocktailsLiveData.value = result.data.map {
                        it.toCocktailUiState(
                            onClick = { navigateToDetail(it.id) }
                        )
                    }
                }
                is RetrieveAlcoholicCocktailsUseCase.UseCaseResult.NetworkError -> {}
            }

            requestNonAlcoholicCocktails()
        }
    }

    private fun requestNonAlcoholicCocktails() {

        viewModelScope.launch {
            when (val result = retrieveNonAlcoholicCocktailsUseCase(Unit)) {
                is RetrieveNonAlcoholicCocktailsUseCase.UseCaseResult.Success -> {
                    _nonAlcoholicCocktailsLiveData.value = result.data.map {
                        it.toCocktailUiState(
                            onClick = { navigateToDetail(it.id) }
                        )
                    }
                }
                is RetrieveNonAlcoholicCocktailsUseCase.UseCaseResult.NetworkError -> {}
            }

            requestFeaturedCocktails()
        }
    }

    private fun requestFeaturedCocktails() {
        viewModelScope.launch {
            when (val result = retrieveRandomCocktailsUseCase(Unit)) {
                is RetrieveRandomCocktailsUseCase.UseCaseResult.Success -> {
                    _featuredCocktailsLiveData.value = result.data.map {
                        it.toCocktailDetailUiState(
                            onClick = { navigateToDetail(it.id) }
                        )
                    }
                }
                is RetrieveRandomCocktailsUseCase.UseCaseResult.NetworkError -> {}
            }
        }
    }


    fun requestFavoriteCocktails() {

        viewModelScope.launch {
            when(val result = retrieveFavoriteCocktailsUseCase(Unit)) {
                is RetrieveFavoriteCocktailsUseCase.UseCaseResult.Success-> {
                    _favoriteCocktailsLiveData.value = result.data.map {
                        it.toCocktailUiState(
                            onClick = { navigateToDetail(it.id) }
                        )
                    }
                }
            }
            _loadingUiStateLiveData.value = LoadingUiState(false)
        }
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