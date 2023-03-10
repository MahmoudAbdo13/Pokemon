package com.mahmoud.pokemon.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mahmoud.pokemon.model.Pokemon;

@Database(entities = Pokemon.class, version = 1, exportSchema = false)
public abstract class PokemonDB extends RoomDatabase {
    public abstract PokemonDao pokemonDao();
}
