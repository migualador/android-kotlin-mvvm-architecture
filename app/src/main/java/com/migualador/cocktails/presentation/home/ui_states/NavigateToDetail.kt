package com.migualador.cocktails.presentation.home.ui_states

sealed class NavigateUiState {
    class NavigateToDetail(val cocktailId: String): NavigateUiState()
}