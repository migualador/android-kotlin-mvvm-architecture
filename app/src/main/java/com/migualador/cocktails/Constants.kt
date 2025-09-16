package com.migualador.cocktails

import com.migualador.cocktails.environments.MockedEnvironment
import com.migualador.cocktails.environments.ProductionEnvironment

class Constants {
    companion object {

        /**
         * You can choose the environment between:
         *
         * ProductionEnvironment: uses actual services in TheCocktailDB. Richer experience.
         *
         * MockedEnvironment: uses mocked responses in a server. The UI experience won't be so rich; the detail of a cocktail will be the same for all
         */
        val environment = ProductionEnvironment()

        const val THE_COCKTAIL_DB_API_BASE_URL = "https://www.thecocktaildb.com/api/json/v1/"

    }
}