package com.mahmoud.pokemon.di;

import android.app.Application;

import androidx.room.Room;

import com.mahmoud.pokemon.db.PokemonDB;
import com.mahmoud.pokemon.db.PokemonDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public static PokemonDB provideDB(Application application){
       return Room.databaseBuilder(application,PokemonDB.class, "FavPokemon")
               .fallbackToDestructiveMigration()
               .allowMainThreadQueries()
               .build();
    }

    @Provides
    @Singleton
    public static PokemonDao provideDao(PokemonDB pokemonDB){
        return pokemonDB.pokemonDao();
    }
}
