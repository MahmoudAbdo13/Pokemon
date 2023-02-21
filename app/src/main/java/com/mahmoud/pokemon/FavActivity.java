package com.mahmoud.pokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.mahmoud.pokemon.adapters.PokemonAdapter;
import com.mahmoud.pokemon.databinding.ActivityFavBinding;
import com.mahmoud.pokemon.databinding.ActivityMainBinding;
import com.mahmoud.pokemon.model.Pokemon;
import com.mahmoud.pokemon.viewmodels.PokemonViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavActivity extends AppCompatActivity {


    private ActivityFavBinding binding;
    private PokemonAdapter adapter;
    private PokemonViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new PokemonAdapter(this);
        binding.favRecyclerView.setAdapter(adapter);
        setupSwipe();

        binding.toHomeButton.setOnClickListener(view -> startActivity(new Intent(FavActivity.this, MainActivity.class)));

        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        viewModel.getFavPokemons();
        viewModel.getFavList().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                ArrayList<Pokemon> list = new ArrayList<>();
                list.addAll(pokemons);
                adapter.setList(list);
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
                viewModel.deletePokemon(swipedPokemon.getName());
                adapter.notifyDataSetChanged();
                Toast.makeText(FavActivity.this, "Pokemon deleted from database", Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.favRecyclerView);
    }
}