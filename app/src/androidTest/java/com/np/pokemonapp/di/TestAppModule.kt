package com.np.pokemonapp.di

import android.content.Context
import androidx.room.Room
import com.np.pokemonapp.datasource.local.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, PokemonDatabase::class.java)
            .allowMainThreadQueries().build()
}