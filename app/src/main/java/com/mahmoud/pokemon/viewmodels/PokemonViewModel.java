package com.mahmoud.pokemon.viewmodels;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mahmoud.pokemon.model.Pokemon;
import com.mahmoud.pokemon.model.PokemonResponse;
import com.mahmoud.pokemon.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class PokemonViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<ArrayList<Pokemon>> pokemList = new MutableLiveData<>();
    private LiveData<List<Pokemon>> favList = null;

    @ViewModelInject
    public PokemonViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<Pokemon>> getPokemList() {
        return pokemList;
    }

    public void getPokemons(){
        repository.getPokemons()
                .subscribeOn(Schedulers.io())
                .map(new Function<PokemonResponse, ArrayList<Pokemon>>() {
                    @Override
                    public ArrayList<Pokemon> apply(PokemonResponse pokemonResponse) throws Throwable {
                        ArrayList<Pokemon> list = pokemonResponse.getResults();
                        for (Pokemon pokemon : list){
                            String url = pokemon.getUrl();
                            String[] pokemonIndex = url.split("/");
                            pokemon.setUrl("https://pokeres.bastionbot.org/images/pokemon/"
                                    +pokemonIndex[pokemonIndex.length-1] +".png");
                        }
                        return list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> pokemList.setValue(result),
                        error -> Log.e("viewModel: ", error.getMessage()));
    }

    public void insertPokemon(Pokemon pokemon){
        repository.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName){repository.deletePokemon(pokemonName);}

    public void getFavPokemons(){favList = repository.getFavPokemons();}
}
