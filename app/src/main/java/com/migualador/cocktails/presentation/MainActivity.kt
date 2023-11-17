package com.migualador.cocktails.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.migualador.cocktails.CocktailsApp
import com.migualador.cocktails.R
import com.migualador.cocktails.databinding.ActivityMainBinding
import com.migualador.cocktails.other.logging.Logger
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var logger: Logger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        (application as CocktailsApp).appComponent.inject(this)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        logger.log("onCreate")
    }

}