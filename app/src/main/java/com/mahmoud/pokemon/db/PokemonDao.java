package com.mahmoud.pokemon.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mahmoud.pokemon.model.Pokemon;

import java.util.List;

@Dao
public interface PokemonDao {

    @Insert
    void insertPokemon(Pokemon pokemon);

    @Query("Delete from fav_table where name =:pokemonName")
    void deletePokemon(String pokemonName);

    @Query("select * from fav_table")
    LiveData<List<Pokemon>> getPokemons();
}
