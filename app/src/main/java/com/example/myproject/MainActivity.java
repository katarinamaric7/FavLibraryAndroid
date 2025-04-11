package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myproject.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Button mapsButton;

    private Button searchButton;

    private DrawerLayout drawerLayout;

    private ImageButton buttonDrawerToggle;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageView iconIv = findViewById(R.id.iconIv);
        TextView welcomeText = findViewById(R.id.welcomeText);
        mapsButton = findViewById(R.id.mapsButton);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });

        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.info) {
                replaceFragment(new AboutFragment());
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                iconIv.setVisibility(View.GONE);
                welcomeText.setVisibility(View.GONE);
                mapsButton.setVisibility(View.GONE);
                searchButton.setVisibility(View.GONE);
            } else if (item.getItemId() == R.id.home) {
                iconIv.setVisibility(View.VISIBLE);
                welcomeText.setVisibility(View.VISIBLE);
                mapsButton.setVisibility(View.VISIBLE);
                searchButton.setVisibility(View.VISIBLE);
                binding.fragmentContainer.setVisibility(View.GONE);

            }
            return true;
        });

        drawerLayout = findViewById(R.id.drawerLayout);
        buttonDrawerToggle = findViewById(R.id.buttonDrawerToggle);

        buttonDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });


        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.row_tbr) {
                    Toast.makeText(MainActivity.this, "TBR clicked", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, TBRActivity.class));
                }

                if(menuItem.getItemId() == R.id.row_heart){
                    Toast.makeText(MainActivity.this, "Favorite clicked", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
                }

                if(menuItem.getItemId() == R.id.row_journal){
                    Toast.makeText(MainActivity.this, "Journal clicked", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, MyJournalActivity.class));
                }


                drawerLayout.close();
                return false;
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

}