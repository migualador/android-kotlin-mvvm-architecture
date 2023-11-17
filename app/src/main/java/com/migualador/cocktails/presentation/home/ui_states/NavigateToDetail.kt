package com.migualador.cocktails.presentation.home.ui_states

sealed class NavigateUiState {
    class NavigateToDetail(val cocktailId: String): NavigateUiState()
    data object NavigateToAlcoholicCocktailsList : NavigateUiState()
    data object NavigateToNonAlcoholicCocktailsList : NavigateUiState()
    data object NavigateToFavoriteCocktailsList : NavigateUiState()
}