package com.mahmoud.pokemon.repository;

import com.mahmoud.pokemon.model.PokemonResponse;
import com.mahmoud.pokemon.network.PokemonApiService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;


public class Repository {
    private PokemonApiService pokemonApiService;

    @Inject
    public Repository(PokemonApiService pokemonApiService) {
        this.pokemonApiService = pokemonApiService;
    }

    public Observable<PokemonResponse> getPokemons(){
        return pokemonApiService.getPokemons();
    }
}
