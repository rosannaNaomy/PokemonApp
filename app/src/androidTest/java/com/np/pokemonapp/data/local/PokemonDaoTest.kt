package com.np.pokemonapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.np.pokemonapp.datasource.local.PokemonDao
import com.np.pokemonapp.datasource.local.PokemonDatabase
import com.np.pokemonapp.datasource.local.entities.PokemonEntry
import com.np.pokemonapp.getOrAwaitValueTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class PokemonDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: PokemonDatabase
    private lateinit var dao: PokemonDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.pokemonDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertPokemon() = runBlockingTest {
        val pokemonEntry = PokemonEntry(1,"name","url")
        dao.insertPokemon(pokemonEntry)

        val allPokemons = dao.observeAllPokemonEntries().getOrAwaitValueTest()

        assertThat(allPokemons).contains(pokemonEntry)
    }

    @Test
    fun deleteAllPokemon() = runBlockingTest {
        val pokemonEntry = PokemonEntry(1,"name","url")
        dao.insertPokemon(pokemonEntry)
        dao.deleteAll()

        val allPokemons = dao.observeAllPokemonEntries().getOrAwaitValueTest()

        assertThat(allPokemons).doesNotContain(pokemonEntry)
    }


}