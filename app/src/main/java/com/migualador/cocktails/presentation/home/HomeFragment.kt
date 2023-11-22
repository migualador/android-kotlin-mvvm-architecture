package com.migualador.cocktails.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.migualador.cocktails.CocktailsApp
import com.migualador.cocktails.R
import com.migualador.cocktails.databinding.FragmentHomeBinding
import com.migualador.cocktails.presentation.cocktails_list.CocktailsListFragment
import com.migualador.cocktails.presentation.home.adapters.CocktailsAdapter
import com.migualador.cocktails.presentation.home.adapters.FeaturedCocktailsPagerAdapter
import com.migualador.cocktails.presentation.home.ui_states.NavigateUiState
import javax.inject.Inject

/**
 * HomeFragment
 *
 */
class HomeFragment: Fragment() {

    @Inject
    lateinit var viewModel: HomeViewModel

    private lateinit var binding: FragmentHomeBinding

    private lateinit var navController: NavController

    private var featuredCocktailsPagerAdapter: FeaturedCocktailsPagerAdapter? = null

    private var alcoholicCocktailsAdapter: CocktailsAdapter? = null

    private var nonAlcoholicCocktailsAdapter: CocktailsAdapter? = null

    private var favoriteCocktailsAdapter: CocktailsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injectDependencies()
        binding = FragmentHomeBinding.inflate(inflater)
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
        setupListeners()
        initializeAdapters()

        viewModel.requestUiState()
    }


    private fun setupObservers() {
        with(viewModel) {

            featuredCocktailsLiveData.observe(viewLifecycleOwner) { cocktailDetailsUiStateList ->
                binding.dotsView.setNumberOfPages(cocktailDetailsUiStateList.size)
                binding.dotsView.setSelectedPage(0)
                featuredCocktailsPagerAdapter?.setData(cocktailDetailsUiStateList)
            }

            alcoholicCocktailsLiveData.observe(viewLifecycleOwner) { cocktailsUiStateList ->
                alcoholicCocktailsAdapter?.setData(cocktailsUiStateList)
            }

            nonAlcoholicCocktailsLiveData.observe(viewLifecycleOwner) { cocktailsUiStateList ->
                nonAlcoholicCocktailsAdapter?.setData(cocktailsUiStateList)
            }

            favoriteCocktailsLiveData.observe(viewLifecycleOwner) { cocktailsUiStateList ->
                if (cocktailsUiStateList.isEmpty()) {
                    binding.favoriteCocktailsCardView.visibility = View.VISIBLE
                    binding.favoriteCocktailsRecyclerView.visibility = View.GONE
                    binding.favoriteCocktailsTextView.isEnabled = false
                } else {
                    binding.favoriteCocktailsCardView.visibility = View.GONE
                    binding.favoriteCocktailsRecyclerView.visibility = View.VISIBLE
                    binding.favoriteCocktailsTextView.isEnabled = true
                    favoriteCocktailsAdapter?.setData(cocktailsUiStateList)
                }
            }

            navigateLiveData.observe(viewLifecycleOwner) { navigateToDetailUiStateEvent ->
                navigateToDetailUiStateEvent.getContentIfNotHandled()?.let {

                    when (it) {
                        is NavigateUiState.NavigateToDetail -> navigateToCocktailDetail(it.cocktailId)
                        is NavigateUiState.NavigateToAlcoholicCocktailsList -> navigateToAlcoholicCocktailsList()
                        is NavigateUiState.NavigateToNonAlcoholicCocktailsList -> navigateToNonAlcoholicCocktailsList()
                        is NavigateUiState.NavigateToFavoriteCocktailsList -> navigateToFavoriteCocktailsList()
                    }

                }
            }

            loadingUiStateLiveData.observe(viewLifecycleOwner) { loadingUiStateEvent ->
                loadingUiStateEvent.getContentIfNotHandled()?.let {
                    binding.progressBar.visibility = if (it.loading) View.VISIBLE else View.GONE
                    binding.mainLayout.visibility = if (it.loading) View.GONE else View.VISIBLE
                }
            }

        }

    }

    private fun setupListeners() {

        binding.alcoholicCocktailsTextView.setOnClickListener {
            viewModel.alcoholicCocktailsHeaderPressed()
        }

        binding.nonAlcoholicCocktailsTextView.setOnClickListener {
            viewModel.nonAlcoholicCocktailsHeaderPressed()
        }

        binding.favoriteCocktailsTextView.setOnClickListener {
            viewModel.favoriteCocktailsHeaderPressed()
        }

    }

    private fun initializeAdapters() {

            featuredCocktailsPagerAdapter = FeaturedCocktailsPagerAdapter(requireContext())
            binding.featuredCocktailsViewPager.adapter = featuredCocktailsPagerAdapter
            binding.featuredCocktailsViewPager.addOnPageChangeListener( object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    binding.dotsView.setSelectedPage(position)
                }
            })


            alcoholicCocktailsAdapter = CocktailsAdapter(requireContext())
            binding.alcoholicCocktailsRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            binding.alcoholicCocktailsRecyclerView.adapter = alcoholicCocktailsAdapter


            nonAlcoholicCocktailsAdapter = CocktailsAdapter(requireContext())
            binding.nonAlcoholicCocktailsRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            binding.nonAlcoholicCocktailsRecyclerView.adapter = nonAlcoholicCocktailsAdapter


            favoriteCocktailsAdapter = CocktailsAdapter(requireContext())
            binding.favoriteCocktailsRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            binding.favoriteCocktailsRecyclerView.adapter = favoriteCocktailsAdapter

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