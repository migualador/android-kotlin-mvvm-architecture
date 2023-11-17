package com.migualador.cocktails.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.migualador.cocktails.CocktailsApp
import com.migualador.cocktails.R
import com.migualador.cocktails.databinding.FragmentSplashBinding
import javax.inject.Inject

/**
 * SplashFragment
 *
 */
class SplashFragment: Fragment() {


    @Inject
    lateinit var viewModel: SplashViewModel

    private lateinit var binding: FragmentSplashBinding

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injectDependencies()

        binding = FragmentSplashBinding.inflate(inflater)
        return binding.root
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
        viewModel.startInitialization()
    }

    private fun setupObservers() {
        viewModel.initializationSuccessLiveData.observe(viewLifecycleOwner) { splashUiState ->
            if (splashUiState.initializationFinishedWithSuccess)
                navController.navigate(R.id.action_splashFragment_to_homeFragment)
            else {
                view?.let { v ->
                    Snackbar
                        .make(v, getString(R.string.initialization_error), Snackbar.LENGTH_INDEFINITE)
                        .show()
                }
            }
        }
    }

}