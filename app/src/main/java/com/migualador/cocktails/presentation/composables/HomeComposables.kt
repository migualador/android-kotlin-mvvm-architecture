package com.migualador.cocktails.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migualador.cocktails.R
import com.migualador.cocktails.presentation.home.ui_states.CocktailUiState

object HomeComposables {

    @Composable
    fun FavoriteCocktails(
        cocktailsList: List<CocktailUiState>
    ) {

        if (cocktailsList.isEmpty()) {
            NoFavoritesCard()
        } else {
            Composables.Cocktails(cocktailsList, "favorite_cocktails_list")
        }
    }

    @Composable
    fun NoFavoritesCard() {
        Card(modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(100.dp),
            shape = RoundedCornerShape(6.dp),) {
            Box(
                Modifier
                    .background(colorResource(R.color.card_background))
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    text = stringResource(R.string.home_favorites_empty),
                    fontSize = 12.sp,
                    color = colorResource(R.color.white),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}