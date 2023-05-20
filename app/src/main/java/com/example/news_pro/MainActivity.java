package com.example.news_pro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.news_pro.Models.NewsApiResponse;
import com.example.news_pro.Models.NewsHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener{

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading News..");
        dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,"general",null);
    }

    //in order to manage our request we are going to use retrofit library.
    //for avoiding hijibiji we can create a new class for all api calls kind of stuffs.

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            showNews(list);
            dialog.dismiss();
        }



        @Override
        public void onError(String message) {

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
}