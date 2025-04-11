package com.example.myproject;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavorites;
    private ArrayList<String> favoriteBooksList;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static final String FAVORITES_KEY = "favorites";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerViewFavorites = findViewById(R.id.recyclerViewBooks);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        Set<String> favorites = sharedPreferences.getStringSet(FAVORITES_KEY, new HashSet<>());

        favoriteBooksList = new ArrayList<>(favorites);

        FavoritesAdapter adapter = new FavoritesAdapter(favoriteBooksList);
        recyclerViewFavorites.setAdapter(adapter);
    }
}
