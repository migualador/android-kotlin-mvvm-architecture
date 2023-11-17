package com.migualador.cocktails.di.components

import androidx.fragment.app.Fragment
import com.migualador.cocktails.di.modules.ViewModelModule
import com.migualador.cocktails.presentation.detail.DetailFragment
import com.migualador.cocktails.presentation.home.HomeFragment
import com.migualador.cocktails.presentation.splash.SplashFragment
import com.migualador.cocktails.presentation.cocktails_list.CocktailsListFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
interface ViewModelsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): ViewModelsComponent
    }

    fun inject(homeFragment: HomeFragment)
    fun inject(splashFragment: SplashFragment)
    fun inject(detailFragment: DetailFragment)
    fun inject(cocktailsListFragment: CocktailsListFragment)
}