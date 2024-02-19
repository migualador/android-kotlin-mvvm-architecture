package com.migualador.cocktails.presentation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.migualador.cocktails.R
import com.migualador.cocktails.presentation.home.ui_states.CocktailDetailUiState
import com.migualador.cocktails.presentation.home.ui_states.CocktailUiState

object Composables {

    @Composable
    fun TopBar() {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 19.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.home_title_a),
                fontSize = 18.sp,
                color = colorResource(R.color.secondary_text_color)
            )

            Text(
                text = stringResource(R.string.home_title_b),
                fontSize = 18.sp,
                color = colorResource(R.color.primary_text_color)
            )
        }
    }

    @Composable
    fun Header(
        headerTextResId: Int,
        onClick: () -> Unit
    ) {

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = onClick
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 4.dp),
                text = stringResource(headerTextResId),
                fontSize = 14.sp,
                color = colorResource(R.color.secondary_text_color)
            )
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun FeaturedCocktails(featuredCocktailsList: List<CocktailDetailUiState>) {
        val pagerState = rememberPagerState(pageCount = { featuredCocktailsList.size })

        HorizontalPager(
            modifier = Modifier
                .height(150.dp),
            state = pagerState
        ) { page ->
            FeaturedCocktailItem(cocktailDetailUiState = featuredCocktailsList[page])
        }

        PagerDots(featuredCocktailsList.size, pagerState.currentPage)
    }

    @Composable
    fun Cocktails(
        cocktailsList: List<CocktailUiState>
    ) {
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .height(170.dp)
        ) {
            items(cocktailsList) { cocktail ->
                CocktailItem(cocktail)
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun CocktailItem(cocktailUiState: CocktailUiState) {
        Card(
            modifier = Modifier
                .width(150.dp),
            shape = RoundedCornerShape(6.dp),
            onClick = cocktailUiState.onClick
        ) {
            Column {
                GlideImage(
                    model = cocktailUiState.imageUrl,
                    contentDescription = cocktailUiState.name,
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp),
                    loading = placeholder(ColorPainter(colorResource(R.color.background_dark)))
                )

                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    text = cocktailUiState.name,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.grey),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }
    }


    @OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun FeaturedCocktailItem(cocktailDetailUiState: CocktailDetailUiState) {
        Box {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(6.dp),
                onClick = cocktailDetailUiState.onClick
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.white))
                ) {
                    GlideImage(
                        model = cocktailDetailUiState.imageUrl,
                        contentDescription = cocktailDetailUiState.drinkName,
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp),
                        loading = placeholder(ColorPainter(colorResource(R.color.white)))
                    )

                    Column {
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            text = cocktailDetailUiState.drinkName,
                            fontSize = 16.sp,
                            color = colorResource(R.color.black),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        FeaturedCocktailInfoLine(
                            R.drawable.ic_category_mini,
                            cocktailDetailUiState.category
                        )
                        FeaturedCocktailInfoLine(
                            if (cocktailDetailUiState.isAlcoholic) R.drawable.ic_alcohol_mini
                            else R.drawable.ic_no_alcohol_mini,
                            if (cocktailDetailUiState.isAlcoholic) stringResource(id = R.string.alcoholic_drink)
                            else stringResource(id = R.string.non_alcoholic_drink)
                        )
                        FeaturedCocktailInfoLine(
                            R.drawable.ic_glass_mini,
                            cocktailDetailUiState.glassType
                        )

                    }

                }

            }

            Image(
                modifier = Modifier.padding(horizontal = 16.dp),
                painter = painterResource(id = R.drawable.label_new),
                contentDescription = "featured"
            )
        }
    }

    @Composable
    fun FeaturedCocktailInfoLine(
        iconResId: Int,
        value: String
    ) {
        Row {
            Image(
                modifier = Modifier.padding(horizontal = 8.dp),
                painter = painterResource(id = iconResId),
                contentDescription = value
            )

            Text(
                //modifier = Modifier.padding(),
                text = value,
                fontSize = 12.sp,
                color = colorResource(R.color.black),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    @Composable
    fun PagerDots(size: Int, selectedIndex: Int) {
        Box(modifier = Modifier.height(15.dp).fillMaxWidth()) {
            Row(
                modifier = Modifier.align(Alignment.Center)
            ) {
                for (i in 0 until size) {
                    Image(
                        modifier = Modifier.padding(horizontal = 2.dp)
                            .align(Alignment.CenterVertically),
                        painter = painterResource(id = if (i == selectedIndex) R.drawable.dot_big else R.drawable.dot_small),
                        contentDescription = "dot"
                    )
                }
            }
        }

    }

}