package com.np.pokemonapp.util.di

import android.content.Context
import androidx.room.Room
import com.np.pokemonapp.datasource.local.PokemonDao
import com.np.pokemonapp.datasource.local.PokemonDatabase
import com.np.pokemonapp.datasource.network.PokeAPI
import com.np.pokemonapp.repository.PokemonRepository
import com.np.pokemonapp.util.Constants.BASE_URL
import com.np.pokemonapp.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonDao(
        database: PokemonDatabase
    ) = database.pokemonDao()

    @Singleton
    @Provides
    fun providePokemonDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, PokemonDatabase::class.java, DATABASE_NAME).build()


    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokeAPI,
        dao: PokemonDao
    ) = PokemonRepository(api, dao)

    @Singleton
    @Provides
    fun providePokeApi(): PokeAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeAPI::class.java)
    }
}