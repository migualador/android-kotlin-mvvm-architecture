package com.migualador.cocktails.di.modules

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.migualador.cocktails.domain.usecases.alcoholic_cocktails.RetrieveAlcoholicCocktailsUseCase
import com.migualador.cocktails.domain.usecases.cocktail_detail.RetrieveCocktailDetailUseCase
import com.migualador.cocktails.domain.usecases.non_alcoholic_cocktails.RetrieveNonAlcoholicCocktailsUseCase
import com.migualador.cocktails.domain.usecases.random_cocktails.RetrieveRandomCocktailsUseCase
import com.migualador.cocktails.domain.usecases.favorite_cocktails.AddFavoriteCocktailUseCase
import com.migualador.cocktails.domain.usecases.favorite_cocktails.IsCocktailInFavoriteCocktailsUseCase
import com.migualador.cocktails.domain.usecases.favorite_cocktails.RemoveFavoriteCocktailUseCase
import com.migualador.cocktails.domain.usecases.favorite_cocktails.RetrieveFavoriteCocktailsUseCase
import com.migualador.cocktails.presentation.detail.DetailViewModel
import com.migualador.cocktails.presentation.detail.DetailViewModelFactory
import com.migualador.cocktails.presentation.home.HomeViewModel
import com.migualador.cocktails.presentation.home.HomeViewModelFactory
import com.migualador.cocktails.presentation.splash.SplashViewModel
import com.migualador.cocktails.presentation.splash.SplashViewModelFactory
import com.migualador.cocktails.presentation.cocktails_list.CocktailsListViewModel
import com.migualador.cocktails.presentation.cocktails_list.CocktailsListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideSplashViewModel(fragment: Fragment): SplashViewModel {
        val factory = SplashViewModelFactory()
        return ViewModelProvider(fragment, factory)[SplashViewModel::class.java]
    }

    @Provides
    fun provideHomeViewModel(
        fragment: Fragment,
        retrieveFavoriteCocktailsUseCase: RetrieveFavoriteCocktailsUseCase,
        retrieveAlcoholicCocktailsUseCase: RetrieveAlcoholicCocktailsUseCase,
        retrieveNonAlcoholicCocktailsUseCase: RetrieveNonAlcoholicCocktailsUseCase,
        retrieveRandomCocktailsUseCase: RetrieveRandomCocktailsUseCase
    ): HomeViewModel {
        val factory = HomeViewModelFactory(
            retrieveFavoriteCocktailsUseCase,
            retrieveAlcoholicCocktailsUseCase,
            retrieveNonAlcoholicCocktailsUseCase,
            retrieveRandomCocktailsUseCase)
        return ViewModelProvider(fragment, factory)[HomeViewModel::class.java]
    }

    @Provides
    fun provideFeaturedDetailViewModel(
        fragment: Fragment,
        isCocktailInFavoriteCocktailsUseCase: IsCocktailInFavoriteCocktailsUseCase,
        addFavoriteCocktailUseCase: AddFavoriteCocktailUseCase,
        removeFavoriteCocktailUseCase: RemoveFavoriteCocktailUseCase,
        retrieveCocktailDetailUseCase: RetrieveCocktailDetailUseCase
    ): DetailViewModel {
        val factory = DetailViewModelFactory(
            isCocktailInFavoriteCocktailsUseCase,
            addFavoriteCocktailUseCase,
            removeFavoriteCocktailUseCase,
            retrieveCocktailDetailUseCase)
        return ViewModelProvider(fragment,factory)[DetailViewModel::class.java]
    }

    @Provides
    fun provideCocktailsListViewModel(
        fragment: Fragment,
        retrieveAlcoholicCocktailsUseCase: RetrieveAlcoholicCocktailsUseCase,
        retrieveNonAlcoholicCocktailsUseCase: RetrieveNonAlcoholicCocktailsUseCase,
        retrieveFavoriteCocktailsUseCase: RetrieveFavoriteCocktailsUseCase
    ): CocktailsListViewModel {
        val factory = CocktailsListViewModelFactory(
            retrieveAlcoholicCocktailsUseCase,
            retrieveNonAlcoholicCocktailsUseCase,
            retrieveFavoriteCocktailsUseCase)
        return ViewModelProvider(fragment, factory)[CocktailsListViewModel::class.java]
    }

}