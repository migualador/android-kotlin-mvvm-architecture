package com.migualador.cocktails.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.migualador.cocktails.CocktailsApp
import com.migualador.cocktails.R
import com.migualador.cocktails.presentation.cocktails_list.CocktailsListFragment
import com.migualador.cocktails.presentation.composables.Composables
import com.migualador.cocktails.presentation.composables.HomeComposables
import com.migualador.cocktails.presentation.home.ui_states.LoadingUiState
import com.migualador.cocktails.presentation.home.ui_states.NavigateUiState
import javax.inject.Inject

/**
 * HomeFragment
 *
 */
class HomeFragment: Fragment() {

    @Inject
    lateinit var viewModel: HomeViewModel

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injectDependencies()

        return ComposeView(
            requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Screen()
            }
        }
    }

    private fun injectDependencies() {
        (activity?.application as CocktailsApp).appComponent
            .getViewModelComponent()
            .create(this)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        setupObservers()
        viewModel.requestFavoriteCocktails()
    }

    @Composable
    fun Screen() {
        val loadingUiState by viewModel.loadingUiStateLiveData.observeAsState(false)
        val isLoading = (loadingUiState as LoadingUiState).loading

        if (isLoading) {
            ScreenLoading()

        } else {
            ScreenReady()
        }
    }

    @Composable
    fun ScreenLoading() {
        Column(modifier = Modifier.testTag("loading_indicator")) {
            Spacer(modifier = Modifier.height(30.dp))

            Composables.TopBar()

            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                CircularProgressIndicator(
                    color = colorResource(R.color.progress_color)
                )
            }
        }
    }

    @Composable
    fun ScreenReady() {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
                .testTag("screen_loaded")
        ) {


            val featuredCocktailsList by viewModel.featuredCocktailsLiveData.observeAsState(
                initial = emptyList()
            )
            val alcoholicCocktailsList by viewModel.alcoholicCocktailsLiveData.observeAsState(
                initial = emptyList()
            )
            val nonAlcoholicCocktailsList by viewModel.nonAlcoholicCocktailsLiveData.observeAsState(
                initial = emptyList()
            )
            val favoriteCocktailsList by viewModel.favoriteCocktailsLiveData.observeAsState(
                initial = emptyList()
            )

            Spacer(modifier = Modifier.height(30.dp))

            Composables.TopBar()

            Composables.FeaturedCocktails(featuredCocktailsList)

            Composables.Header(R.string.home_alcoholic_cocktails) { navigateToAlcoholicCocktailsList() }

            Composables.Cocktails(alcoholicCocktailsList, listTestTag = "alcoholic_cocktails_list")

            Composables.Header(R.string.home_non_alcoholic_cocktails) { navigateToNonAlcoholicCocktailsList() }

            Composables.Cocktails(nonAlcoholicCocktailsList, listTestTag = "non_alcoholic_cocktails_list")

            Composables.Header(R.string.home_favorite_cocktails) { navigateToFavoriteCocktailsList() }

            HomeComposables.FavoriteCocktails(favoriteCocktailsList)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    private fun setupObservers() {
        with(viewModel) {

            navigateLiveData.observe(viewLifecycleOwner) { navigateToDetailUiStateEvent ->
                navigateToDetailUiStateEvent.getContentIfNotHandled()?.let {

                    when (it) {
                        is NavigateUiState.NavigateToDetail -> navigateToCocktailDetail(it.cocktailId)
                    }
                }
            }

        }
    }

    private fun navigateToCocktailDetail(cocktailId: String) {
        val bundle = Bundle()
        bundle.putString("id", cocktailId)
        navController.navigate(R.id.action_homeFragment_to_featuredDetailFragment, bundle)
    }

    private fun navigateToAlcoholicCocktailsList() {
        val bundle = Bundle()
        bundle.putString("listType", CocktailsListFragment.Companion.ListType.ALCOHOLIC_COCKTAILS_LIST.name)
        navController.navigate(R.id.action_homeFragment_to_cocktailListFragment, bundle)
    }

    private fun navigateToNonAlcoholicCocktailsList() {
        val bundle = Bundle()
        bundle.putString("listType", CocktailsListFragment.Companion.ListType.NON_ALCOHOLIC_COCKTAILS_LIST.name)
        navController.navigate(R.id.action_homeFragment_to_cocktailListFragment, bundle)
    }

    private fun navigateToFavoriteCocktailsList() {
        val bundle = Bundle()
        bundle.putString("listType", CocktailsListFragment.Companion.ListType.FAVORITE_COCKTAILS_LIST.name)
        navController.navigate(R.id.action_homeFragment_to_cocktailListFragment, bundle)
    }

}