package com.example.myproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private ArrayList<BookInfo> bookInfoArrayList;
    private EditText searchEdt;
    private ImageButton searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEdt = findViewById(R.id.idEdtSearchBooks);
        searchBtn = findViewById(R.id.idBtnSearch);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (searchEdt.getText().toString().isEmpty()) {
                    searchEdt.setError("Please enter search query");
                    return;
                }
                getBooksInfo(searchEdt.getText().toString());
            }
        });
    }

    private void getBooksInfo(String query) {

        bookInfoArrayList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(SearchActivity.this);

        mRequestQueue.getCache().clear();

        String url = "https://www.googleapis.com/books/v1/volumes?q=" + query;

        RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);


        JsonObjectRequest booksObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has("items")) {
                        JSONArray itemsArray = response.getJSONArray("items");
                        for (int i = 0; i < itemsArray.length(); i++) {
                            JSONObject itemsObj = itemsArray.getJSONObject(i);
                            JSONObject volumeObj = itemsObj.getJSONObject("volumeInfo");
                            String title = volumeObj.optString("title");
                            String subtitle = volumeObj.optString("subtitle");

                            JSONArray authorsArray = volumeObj.optJSONArray("authors");
                            ArrayList<String> authorsArrayList = new ArrayList<>();
                            if (authorsArray != null) {
                                for (int j = 0; j < authorsArray.length(); j++) {
                                    authorsArrayList.add(authorsArray.optString(j));
                                }
                            }

                            String publisher = volumeObj.optString("publisher");
                            String publishedDate = volumeObj.optString("publishedDate");
                            String description = volumeObj.optString("description");
                            int pageCount = volumeObj.optInt("pageCount");
                            JSONObject imageLinks = volumeObj.optJSONObject("imageLinks");
                            String thumbnail = null;
                            if (imageLinks != null) {
                                thumbnail = imageLinks.optString("thumbnail");
                            }
                            String previewLink = volumeObj.optString("previewLink");
                            String infoLink = volumeObj.optString("infoLink");

                            BookInfo bookInfo = new BookInfo(title, subtitle, authorsArrayList, publisher, publishedDate, description, pageCount, thumbnail, previewLink, infoLink);

                            bookInfoArrayList.add(bookInfo);
                        }

                        BookAdapter adapter = new BookAdapter(bookInfoArrayList, SearchActivity.this);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false);
                        RecyclerView mRecyclerView = findViewById(R.id.idRVBooks);

                        mRecyclerView.setLayoutManager(linearLayoutManager);
                        mRecyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(SearchActivity.this, "No books found for your query", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SearchActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(booksObjrequest);
    }
}
