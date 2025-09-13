package com.migualador.cocktails.data.repositories

import com.migualador.cocktails.data.datasources.alcoholic_cocktails.AlcoholicCocktailsDBDataSource
import com.migualador.cocktails.data.datasources.alcoholic_cocktails.AlcoholicCocktailsLocalDataSource
import com.migualador.cocktails.data.datasources.alcoholic_cocktails.AlcoholicCocktailsRemoteDataSource
import com.migualador.cocktails.data.entities.Cocktail
import com.migualador.cocktails.data.network.NetworkResult
import com.migualador.cocktails.other.logging.Logger
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class AlcoholicCocktailsRepositoryTest {

    private val cocktailsList = listOf(
        Cocktail("id", "Afterglow", "https://www.thecocktaildb.com/images/0071_a.jpg"),
        Cocktail("id", "Alice Cocktail", "https://www.thecocktaildb.com/images/897_b.jpg"),
    )

    private val localDataSource = mockk<AlcoholicCocktailsLocalDataSource>(relaxed = true)
    private val remoteDataSource = mockk<AlcoholicCocktailsRemoteDataSource>(relaxed = true)
    private val dbDataSource = mockk<AlcoholicCocktailsDBDataSource>(relaxed = true)
    private val logger = mockk<Logger>(relaxed = true)

    private lateinit var repository: AlcoholicCocktailsRepository

    @Before
    fun setUp() {
        repository = AlcoholicCocktailsRepository(
            localDataSource,
            remoteDataSource,
            dbDataSource,
            logger
        )
    }

    @Test
    fun `When cocktails local datasource has data, repository retrieves data from local datasource`() {

        coEvery {
            localDataSource.getAlcoholicCocktails()
        } returns cocktailsList

        coEvery {
            remoteDataSource.getAlcoholicCocktails()
        } returns NetworkResult.Success(cocktailsList)

        coEvery {
            dbDataSource.getAlcoholicCocktails()
        } returns cocktailsList


        runBlocking {
            repository.getAlcoholicCocktails()
        }

        coVerify(exactly = 1) { localDataSource.getAlcoholicCocktails() }
    }

    @Test
    fun `When cocktails local datasource has data, repository does not retrieve data from remote datasource`() {
        coEvery {
            localDataSource.getAlcoholicCocktails()
        } returns cocktailsList

        coEvery {
            remoteDataSource.getAlcoholicCocktails()
        } returns NetworkResult.Success(cocktailsList)

        coEvery {
            dbDataSource.getAlcoholicCocktails()
        } returns cocktailsList


        runBlocking {
            repository.getAlcoholicCocktails()
        }

        coVerify(exactly = 0) { remoteDataSource.getAlcoholicCocktails() }
    }

    @Test
    fun `When cocktails local datasource has data, repository does not retrieve data from database datasource`() {
        coEvery {
            localDataSource.getAlcoholicCocktails()
        } returns cocktailsList

        coEvery {
            remoteDataSource.getAlcoholicCocktails()
        } returns NetworkResult.Success(cocktailsList)

        coEvery {
            dbDataSource.getAlcoholicCocktails()
        } returns cocktailsList


        runBlocking {
            repository.getAlcoholicCocktails()
        }

        coVerify(exactly = 0) { dbDataSource.getAlcoholicCocktails() }
    }

    @Test
    fun `When cocktails local datasource is empty and remote datasource returns Success, data is retrieved from remote datasource`() {
        coEvery {
            localDataSource.getAlcoholicCocktails()
        } returns emptyList()

        coEvery {
            remoteDataSource.getAlcoholicCocktails()
        } returns NetworkResult.Success(cocktailsList)

        coEvery {
            dbDataSource.getAlcoholicCocktails()
        } returns cocktailsList


        runBlocking {
            repository.getAlcoholicCocktails()
        }

        coVerify(exactly = 1) { remoteDataSource.getAlcoholicCocktails() }
    }

    @Test
    fun `When cocktails local datasource is empty and remote datasource returns http error, data is retrieved from database datasource`() {
        coEvery {
            localDataSource.getAlcoholicCocktails()
        } returns emptyList()

        coEvery {
            remoteDataSource.getAlcoholicCocktails()
        } returns NetworkResult.HttpError(404)

        coEvery {
            dbDataSource.getAlcoholicCocktails()
        } returns cocktailsList


        runBlocking {
            repository.getAlcoholicCocktails()
        }

        coVerify(exactly = 1) { dbDataSource.getAlcoholicCocktails() }
    }

    @Test
    fun `When cocktails local datasource is empty and remote datasource returns connection error, data is retrieved from database datasource`() {
        coEvery {
            localDataSource.getAlcoholicCocktails()
        } returns emptyList()

        coEvery {
            remoteDataSource.getAlcoholicCocktails()
        } returns NetworkResult.ConnectionError()

        coEvery {
            dbDataSource.getAlcoholicCocktails()
        } returns cocktailsList


        runBlocking {
            repository.getAlcoholicCocktails()
        }

        coVerify(exactly = 1) { dbDataSource.getAlcoholicCocktails() }
    }

    @Test
    fun `When cocktails local datasource is empty and remote datasource returns Success, the data retrieved from remote datasource is set in local datasource`() {

        coEvery {
            localDataSource.getAlcoholicCocktails()
        } returns emptyList()

        coEvery {
            remoteDataSource.getAlcoholicCocktails()
        } returns NetworkResult.Success(cocktailsList)

        coEvery {
            dbDataSource.getAlcoholicCocktails()
        } returns emptyList()


        runBlocking {
            repository.getAlcoholicCocktails()
        }

        coVerify(exactly = 1) { localDataSource.setAlcoholicCocktails(cocktailsList) }
    }

    @Test
    fun `When cocktails local datasource is empty and remote datasource returns Success, the data retrieved from remote datasource is set in database datasource`() {

        coEvery {
            localDataSource.getAlcoholicCocktails()
        } returns emptyList()

        coEvery {
            remoteDataSource.getAlcoholicCocktails()
        } returns NetworkResult.Success(cocktailsList)

        coEvery {
            dbDataSource.getAlcoholicCocktails()
        } returns emptyList()


        runBlocking {
            repository.getAlcoholicCocktails()
        }

        coVerify(exactly = 1) { dbDataSource.setAlcoholicCocktails(cocktailsList) }
    }

    @Test
    fun `When cocktails local datasource is not empty, repository getAlcoholicCocktails returns the values contained in local data source`() {

        val expectedResult = cocktailsList

        coEvery {
            localDataSource.getAlcoholicCocktails()
        } returns cocktailsList

        val result = runBlocking {
            repository.getAlcoholicCocktails()
        }

        assert(result == expectedResult)
    }

    @Test
    fun `When cocktails local datasource is empty and remote datasource returns Success, repository getAlcoholicCocktails returns the values contained in remote data source`() {

        val expectedResult = cocktailsList

        coEvery {
            localDataSource.getAlcoholicCocktails()
        } returns emptyList()

        coEvery {
            remoteDataSource.getAlcoholicCocktails()
        } returns NetworkResult.Success(cocktailsList)

        val result = runBlocking {
            repository.getAlcoholicCocktails()
        }

        assert(result == expectedResult)
    }

    @Test
    fun `When cocktails local datasource is empty and remote datasource returns http error and database datasource is not empty, repository getAlcoholicCocktails returns the values contained in database data source`() {

        val expectedResult = cocktailsList

        coEvery {
            localDataSource.getAlcoholicCocktails()
        } returns emptyList()

        coEvery {
            remoteDataSource.getAlcoholicCocktails()
        } returns NetworkResult.HttpError(404)

        coEvery {
            dbDataSource.getAlcoholicCocktails()
        } returns cocktailsList

        val result = runBlocking {
            repository.getAlcoholicCocktails()
        }

        assert(result == expectedResult)
    }
}