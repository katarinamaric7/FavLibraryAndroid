package com.example.myproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BookDetails extends AppCompatActivity {
    String title, subtitle, publisher, publishedDate, description, thumbnail, previewLink, infoLink;
    int pageCount;
    private ArrayList<String> authors;

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static final String FAVORITES_KEY = "favorites";


    TextView titleTV, subtitleTV, publisherTV, descTV, pageTV, publishDateTV;
    Button previewBtn;
    private ImageView bookIV;

    ImageButton favoriteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        titleTV = findViewById(R.id.idTVTitle);
        subtitleTV = findViewById(R.id.idTVSubTitle);
        publisherTV = findViewById(R.id.idTVpublisher);
        descTV = findViewById(R.id.idTVDescription);
        pageTV = findViewById(R.id.idTVNoOfPages);
        publishDateTV = findViewById(R.id.idTVPublishDate);
        previewBtn = findViewById(R.id.idBtnPreview);
        bookIV = findViewById(R.id.idIVbook);
        favoriteBtn = findViewById(R.id.idBtnFavorite);


        title = getIntent().getStringExtra("title");
        subtitle = getIntent().getStringExtra("subtitle");
        publisher = getIntent().getStringExtra("publisher");
        publishedDate = getIntent().getStringExtra("publishedDate");
        description = getIntent().getStringExtra("description");
        pageCount = getIntent().getIntExtra("pageCount", 0);
        thumbnail = getIntent().getStringExtra("thumbnail");
        previewLink = getIntent().getStringExtra("previewLink");
        infoLink = getIntent().getStringExtra("infoLink");


        titleTV.setText(title);
        subtitleTV.setText(subtitle);
        publisherTV.setText(publisher);
        publishDateTV.setText("Published On : " + publishedDate);
        descTV.setText(description);
        pageTV.setText("No Of Pages : " + pageCount);
        Picasso.get().load(thumbnail).into(bookIV);


        previewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (previewLink.isEmpty()) {
                    Toast.makeText(BookDetails.this, "No preview Link present", Toast.LENGTH_SHORT).show();
                    return;
                }
                Uri uri = Uri.parse(previewLink);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<String> favorites = sharedPreferences.getStringSet(FAVORITES_KEY, new HashSet<>());

                if (favorites.contains(title)) {
                    favorites.remove(title);
                    favoriteBtn.setImageResource(R.drawable.heartoutline);
                    Toast.makeText(BookDetails.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                } else {
                    favorites.add(title);
                    favoriteBtn.setImageResource(R.drawable.heart);
                    Toast.makeText(BookDetails.this, "Added to favorites", Toast.LENGTH_SHORT).show();
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet(FAVORITES_KEY, favorites);
                editor.apply();
            }
        });

        Set<String> favorites = sharedPreferences.getStringSet(FAVORITES_KEY, new HashSet<>());
        if (favorites.contains(title)) {
            favoriteBtn.setImageResource(R.drawable.heart);
        } else {
            favoriteBtn.setImageResource(R.drawable.heartoutline);
        }
    }
}
