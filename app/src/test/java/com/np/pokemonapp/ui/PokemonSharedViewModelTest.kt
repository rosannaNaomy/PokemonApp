package com.np.pokemonapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.np.pokemonapp.MainCoroutineRule
import com.np.pokemonapp.getOrAwaitValueTest
import com.np.pokemonapp.repository.FakePokemonRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class PokemonSharedViewModelTest {

    lateinit var fakeRepository: FakePokemonRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: PokemonSharedViewModel

    @Before
    fun setup() {
        fakeRepository = FakePokemonRepository()
        viewModel = PokemonSharedViewModel(fakeRepository)

    }

//    @Test
//    fun `observeDatabasePokemonEntriesList_isNotnull_expectedTrue`() = runBlocking{
//
//        val value = fakeRepository.allPokemonEntriesFromDB().getOrAwaitValueTest()
//        assertThat(value.isNotEmpty()).isTrue()
//    }

    @Test
    fun `ifFetchingPokemonByIDSuccessful_dataIsNotnull_isNotnull_expectedTrue`() = runBlocking {

        val value = fakeRepository.getPokemonInfo(2)
        assertThat(value.data).isNotNull()
    }

    @Test
    fun `ifFetchingPokemonByIDFails_messageIsNotnull_expectedTrue`() = runBlocking {

        fakeRepository.setShouldReturnNetworkError(true)
        val value = fakeRepository.getPokemonInfo(2)

        assertThat(value.message).isNotNull()
    }

    @Test
    fun `checkIf_pokemonIdIsStored_expectedTrue`() = runBlocking {

        val id = 4
        viewModel.fetchSinglePokemon(id)
        viewModel.fetchSinglePokemon(3)

        fakeRepository.getPokemonWithAbilities(id)
        fakeRepository.getPokemonWithAbilities(id)

        val value = fakeRepository.getPokemonWithAbilities(4)

        val name = viewModel.pokemonWithAbilities.getOrAwaitValueTest()


        assertThat(value).isNotNull()
    }

}