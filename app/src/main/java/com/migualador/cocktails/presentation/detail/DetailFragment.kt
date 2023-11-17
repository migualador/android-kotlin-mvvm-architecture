package com.migualador.cocktails.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.migualador.cocktails.CocktailsApp
import com.migualador.cocktails.R
import com.migualador.cocktails.databinding.FragmentDetailBinding
import com.migualador.cocktails.presentation.animation_extensions.animateDelayedFadeIn
import com.migualador.cocktails.presentation.animation_extensions.animateFadeOut
import com.migualador.cocktails.presentation.home.ui_states.CocktailDetailUiState
import javax.inject.Inject

/**
 * DetailFragment
 *
 */
class DetailFragment: Fragment() {

    @Inject
    lateinit var viewModel: DetailViewModel

    private lateinit var binding: FragmentDetailBinding

    private lateinit var navController: NavController

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var selectedLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injectDependencies()
        binding = FragmentDetailBinding.inflate(inflater)
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

        viewModel.obtainCocktailDetail(args.id)

        animateScreenAppear()
    }

    private fun animateScreenAppear() {
        binding.titleTextView.animateDelayedFadeIn()
        binding.subtitleTextView.animateDelayedFadeIn()
        binding.ingredientsLayout.animateDelayedFadeIn()

        selectedLayout = binding.ingredientsLayout

        binding.instructionsLayout.animateFadeOut()
        binding.toggleButtonGroup.check(R.id.toggle1)
    }

    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            viewModel.backButtonPressed()
        }

        binding.favoriteButton.setOnClickListener{
            viewModel.toggleFavorite()
        }

        binding.toggle1.setOnClickListener {
            selectedLayout.animateFadeOut()
            binding.ingredientsLayout.animateDelayedFadeIn()
            selectedLayout = binding.ingredientsLayout
        }

        binding.toggle2.setOnClickListener {
            selectedLayout.animateFadeOut()
            binding.instructionsLayout.animateDelayedFadeIn()
            selectedLayout = binding.instructionsLayout
        }
    }

    private fun setupObservers() {

        viewModel.cocktailDetailLiveData.observe(viewLifecycleOwner) { cocktailDetailUiState ->
            with(cocktailDetailUiState) {
                binding.titleTextView.text = drinkName
                binding.categoryTextView.text = category
                binding.glassTextView.text = glassType

                if (isAlcoholic) {
                    binding.alcoholicTextView.text = ContextCompat.getString(requireContext(), R.string.alcoholic_drink)
                } else {
                    binding.alcoholicTextView.text = ContextCompat.getString(requireContext(), R.string.non_alcoholic_drink)
                    val drawableStart = ContextCompat.getDrawable(requireContext(), R.drawable.ic_no_alcohol)
                    binding.alcoholicTextView.setCompoundDrawablesWithIntrinsicBounds(drawableStart, null, null, null)
                }

                Glide.with(binding.imageView)
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.imageView)

                fillIngredientsTextView(cocktailDetailUiState)
                fillInstructionsTextView(cocktailDetailUiState)
            }
        }

        viewModel.isFavoriteLiveData.observe(viewLifecycleOwner) { inWishListData ->
            binding.favoriteButton.setImageResource(
                if (inWishListData) R.drawable.ic_favorite_on
                else R.drawable.ic_favorite_off
            )
        }

        viewModel.navigateBackLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                navController.popBackStack()
            }
        }
    }

    private fun fillIngredientsTextView(cocktailDetailUiState: CocktailDetailUiState) {
        with(cocktailDetailUiState) {

            val text = StringBuilder()
            for (ingredientsAndMeasure in ingredientsAndMeasures) {
                with(ingredientsAndMeasure) {
                    if (ingredient.isNotEmpty() && measure.isNotEmpty()) {
                        text.append("$measure: $ingredient\n")
                    }
                }
            }

            binding.ingredientsTextView.text = text.toString()
        }
    }

    private fun fillInstructionsTextView(cocktailDetailUiState: CocktailDetailUiState) {
        with(cocktailDetailUiState) {
            binding.instructionsTextView.text = instructions
        }
    }

}