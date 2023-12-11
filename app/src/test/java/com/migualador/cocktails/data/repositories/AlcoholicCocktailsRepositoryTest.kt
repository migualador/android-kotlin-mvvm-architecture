package com.migualador.cocktails.data.repositories

import com.migualador.cocktails.data.datasources.alcoholic_cocktails.AlcoholicCocktailsDBDataSource
import com.migualador.cocktails.data.datasources.alcoholic_cocktails.AlcoholicCocktailsLocalDataSource
import com.migualador.cocktails.data.datasources.alcoholic_cocktails.AlcoholicCocktailsRemoteDataSource
import com.migualador.cocktails.data.entities.Cocktail
import com.migualador.cocktails.data.network.NetworkResult
import com.migualador.cocktails.other.logging.Logger
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verifyBlocking


class AlcoholicCocktailsRepositoryTest {

    private val cocktailsList = listOf(Cocktail("id", "name", "imageUrl"))

    private val logger: Logger = mock()

    private val mockEmptyAlcoholicCocktailsLocalDataSource: AlcoholicCocktailsLocalDataSource =
        mock {
            on { getAlcoholicCocktails()} doReturn emptyList()
        }


    private val mockNotEmptyAlcoholicCocktailsLocalDataSource: AlcoholicCocktailsLocalDataSource =
        mock {
            on { getAlcoholicCocktails() } doReturn cocktailsList
        }

    private val mockAlcoholicCocktailsRemoteDataSourceReturningSuccess: AlcoholicCocktailsRemoteDataSource =
        mock {
            onBlocking { getAlcoholicCocktails() } doReturn NetworkResult.Success(cocktailsList)
        }


    private val mockAlcoholicCocktailsRemoteDataSourceReturningHttpError: AlcoholicCocktailsRemoteDataSource =
        mock {
            onBlocking { getAlcoholicCocktails() } doReturn NetworkResult.HttpError(404)
        }


    private val mockAlcoholicCocktailsRemoteDataSourceReturningConnectionError: AlcoholicCocktailsRemoteDataSource =
        mock {
            onBlocking { getAlcoholicCocktails() } doReturn NetworkResult.ConnectionError()
        }

    private val mockEmptyAlcoholicCocktailsDBDataSource: AlcoholicCocktailsDBDataSource =
         mock {
            on { getAlcoholicCocktails()} doReturn emptyList()
        }

    private val mockNotEmptyAlcoholicCocktailsDBDataSource: AlcoholicCocktailsDBDataSource =
        mock {
            on { getAlcoholicCocktails()} doReturn cocktailsList
        }



    @Test
    fun `When cocktails local datasource is not empty, data is not retrieved from either remote datasource or database datasource`() {
        val repository = AlcoholicCocktailsRepository(
            mockNotEmptyAlcoholicCocktailsLocalDataSource,
            mockAlcoholicCocktailsRemoteDataSourceReturningSuccess,
            mockNotEmptyAlcoholicCocktailsDBDataSource,
            logger
        )

        runBlocking {
            repository.getAlcoholicCocktails()
        }

        verifyBlocking(mockAlcoholicCocktailsRemoteDataSourceReturningSuccess, never()) { getAlcoholicCocktails() }
        verifyBlocking(mockNotEmptyAlcoholicCocktailsDBDataSource, never()) { getAlcoholicCocktails() }
    }

    @Test
    fun `When cocktails local datasource is empty, data is retrieved from remote datasource`() {
        val repository = AlcoholicCocktailsRepository(
            mockEmptyAlcoholicCocktailsLocalDataSource,
            mockAlcoholicCocktailsRemoteDataSourceReturningSuccess,
            mockNotEmptyAlcoholicCocktailsDBDataSource,
            logger
        )

        runBlocking {
            repository.getAlcoholicCocktails()
        }

        verifyBlocking(mockAlcoholicCocktailsRemoteDataSourceReturningSuccess) { getAlcoholicCocktails() }
    }

    @Test
    fun `When cocktails local datasource is empty and remote datasource returns http error, data is retrieved from database datasource`() {
        val repository = AlcoholicCocktailsRepository(
            mockEmptyAlcoholicCocktailsLocalDataSource,
            mockAlcoholicCocktailsRemoteDataSourceReturningHttpError,
            mockNotEmptyAlcoholicCocktailsDBDataSource,
            logger
        )

        runBlocking {
            repository.getAlcoholicCocktails()
        }

        verifyBlocking(mockNotEmptyAlcoholicCocktailsDBDataSource) { getAlcoholicCocktails() }
    }

    @Test
    fun `When cocktails local datasource is empty and remote datasource returns connection error, data is retrieved from database datasource`() {
        val repository = AlcoholicCocktailsRepository(
            mockEmptyAlcoholicCocktailsLocalDataSource,
            mockAlcoholicCocktailsRemoteDataSourceReturningConnectionError,
            mockNotEmptyAlcoholicCocktailsDBDataSource,
            logger
        )

        runBlocking {
            repository.getAlcoholicCocktails()
        }

        verifyBlocking(mockNotEmptyAlcoholicCocktailsDBDataSource) { getAlcoholicCocktails() }
    }

    @Test
    fun `When cocktails local datasource is empty and remote datasource returns Success, the data retrieved from remote datasource is added to local datasource and database datasource`() {

        val repository = AlcoholicCocktailsRepository(
            mockEmptyAlcoholicCocktailsLocalDataSource,
            mockAlcoholicCocktailsRemoteDataSourceReturningSuccess,
            mockEmptyAlcoholicCocktailsDBDataSource,
            logger
        )
        runBlocking {
            repository.getAlcoholicCocktails()
        }

        verifyBlocking(mockEmptyAlcoholicCocktailsLocalDataSource) { setAlcoholicCocktails(any()) }
        verifyBlocking(mockEmptyAlcoholicCocktailsDBDataSource) { setAlcoholicCocktails(any()) }
    }



}