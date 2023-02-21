package com.mahmoud.pokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.mahmoud.pokemon.adapters.PokemonAdapter;
import com.mahmoud.pokemon.databinding.ActivityMainBinding;
import com.mahmoud.pokemon.model.Pokemon;
import com.mahmoud.pokemon.viewmodels.PokemonViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PokemonAdapter adapter;
    private PokemonViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new PokemonAdapter(this);
        binding.pokemonRecyclerView.setAdapter(adapter);
        setupSwipe();

        binding.toFavButton.setOnClickListener(view -> startActivity(new Intent(this, FavActivity.class)));

        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        viewModel.getPokemons();
        viewModel.getPokemList().observe(this, new Observer<ArrayList<Pokemon>>() {
            @Override
            public void onChanged(ArrayList<Pokemon> pokemons) {
                adapter.setList(pokemons);
            }
        });
    }

    private void setupSwipe(){
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPokemonPosition = viewHolder.getAdapterPosition();
                Pokemon swipedPokemon = adapter.getPokemonAt(swipedPokemonPosition);
                viewModel.insertPokemon(swipedPokemon);

                Toast.makeText(MainActivity.this, "Pokemon added to database", Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.pokemonRecyclerView);
    }
}