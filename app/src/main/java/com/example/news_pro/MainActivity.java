package com.example.news_pro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.news_pro.Models.NewsApiResponse;
import com.example.news_pro.Models.NewsHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener{

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    Button b1,b2,b3,b4,b5,b6,b7;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                dialog.setTitle("Fetching news articles of "+ query);
                dialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadlines(listener,"general",query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading News..");
        dialog.show();
        initializeButtons();
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,"general",null);
    }

    private void initializeButtons() {
        b1 = findViewById(R.id.btn_0);
        b1.setOnClickListener(this);
        b2 = findViewById(R.id.btn_1);
        b2.setOnClickListener(this);
        b3 = findViewById(R.id.btn_2);
        b3.setOnClickListener(this);
        b4 = findViewById(R.id.btn_3);
        b4.setOnClickListener(this);
        b5 = findViewById(R.id.btn_4);
        b5.setOnClickListener(this);
        b6 = findViewById(R.id.btn_5);
        b6.setOnClickListener(this);
        b7 = findViewById(R.id.btn_6);
        b7.setOnClickListener(this);
    }

    //in order to manage our request we are going to use retrofit library.
    //for avoiding hijibiji we can create a new class for all api calls kind of stuffs.

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if(list.isEmpty()){
                Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
            else{
                showNews(list);
                dialog.dismiss();
            }


        }



        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, "An Error Occured!!", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter = new CustomAdapter(this,list,this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onNewsClicked(NewsHeadlines headlines) {
        Intent intent = new Intent(MainActivity.this,Details_activity.class);
        intent.putExtra("data",headlines);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String category = button.getText().toString();

        dialog.setTitle("Fetching News articles of " + category);
        dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,category,null);
    }
}