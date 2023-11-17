package com.migualador.cocktails.presentation.cocktails_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.migualador.cocktails.R
import com.migualador.cocktails.CocktailsApp
import com.migualador.cocktails.databinding.FragmentCocktailsListBinding
import com.migualador.cocktails.presentation.home.ui_states.NavigateUiState
import com.migualador.cocktails.presentation.cocktails_list.adapters.CocktailsGridAdapter
import javax.inject.Inject

class CocktailsListFragment: Fragment() {

    companion object {
        enum class ListType {
            ALCOHOLIC_COCKTAILS_LIST,
            NON_ALCOHOLIC_COCKTAILS_LIST,
            FAVORITE_COCKTAILS_LIST
        }
    }

    @Inject
    lateinit var viewModel: CocktailsListViewModel

    private lateinit var binding: FragmentCocktailsListBinding

    private lateinit var navController: NavController

    private lateinit var cocktailsAdapter: CocktailsGridAdapter

    private val args: CocktailsListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injectDependencies()
        binding = FragmentCocktailsListBinding.inflate(inflater)
        return binding.root
    }

    private fun injectDependencies() {
        (activity?.application as CocktailsApp).appComponent.getViewModelComponent().create(this).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        setupObservers()
        setupListeners()
        initializeAdapters()
        when (args.listType) {
            ListType.ALCOHOLIC_COCKTAILS_LIST.name -> {
                binding.topBarTextView.text = getString(R.string.alcoholic_cocktails)
                viewModel.requestAlcoholicCocktails()
            }

            ListType.NON_ALCOHOLIC_COCKTAILS_LIST.name -> {
                binding.topBarTextView.text = getString(R.string.non_alcoholic_cocktails)
                viewModel.requestNonAlcoholicCocktails()
            }

            ListType.FAVORITE_COCKTAILS_LIST.name -> {
                binding.topBarTextView.text = getString(R.string.favorite_cocktails)
                viewModel.requestFavoriteCocktails()
            }
        }


    }

    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            viewModel.backButtonPressed()
        }
    }

    private fun setupObservers() {
        viewModel.cocktailsLiveData.observe(viewLifecycleOwner) { cocktails ->
            cocktailsAdapter.setData(cocktails)
        }

        viewModel.navigateLiveData.observe(viewLifecycleOwner) { navigateToDetailUiStateEvent ->
            navigateToDetailUiStateEvent.getContentIfNotHandled()?.let {

                if (it is NavigateUiState.NavigateToDetail) {
                    navigateToCocktailDetail(it.cocktailId)
                }
            }
        }

        viewModel.navigateBackLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                navController.popBackStack()
            }
        }
    }

    private fun initializeAdapters() {
        cocktailsAdapter = CocktailsGridAdapter(requireContext())
        binding.cocktailsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.cocktailsRecyclerView.adapter = cocktailsAdapter
    }

    private fun navigateToCocktailDetail(cocktailId: String) {
        val bundle = Bundle()
        bundle.putString("id", cocktailId)
        navController.navigate(R.id.action_cocktailsListFragment_to_detailFragment, bundle)
    }
}