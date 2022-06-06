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

    /*
      will only hold the database in the ram
      and not inside the persistence storage,
      only saved for that test case
   */
    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, PokemonDatabase::class.java)
            .allowMainThreadQueries().build()//this function allows access from the main thread,
    // for testing it's okay, because different threads can manipulate each other,
    //and in this case we want tests to execute after one another

}