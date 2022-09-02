package com.example.wallpaperapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ImageModel> modelClasslist;
    private RecyclerView recyclerView;
    private AdView mAdView;
    Adapter adapter;
    CardView mnature, mbus, mcar, mtrain, mtrending;
    EditText editText;
    ImageButton search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView =findViewById(R.id.recyclerview);
        mnature =findViewById(R.id.nature);
        mbus =findViewById(R.id.bus);
        mcar =findViewById(R.id.car);
        mtrain =findViewById(R.id.train);
        mtrending =findViewById(R.id.trending);
        editText =findViewById(R.id.edittext);
        search =findViewById(R.id.search);

        modelClasslist = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        adapter = new Adapter(getApplicationContext(),modelClasslist);
        recyclerView.setAdapter(adapter);
        findphotos();



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);




        mnature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "nature";
                getSearchImage(query);
            }
        });

        mcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "car";
                getSearchImage(query);
            }
        });

        mtrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "train";
                getSearchImage(query);
            }
        });

        mbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "bus";
                getSearchImage(query);
            }
        });

        mtrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findphotos();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editText.getText().toString().trim().toLowerCase();
                if (query.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Enter Something", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    getSearchImage(query);
                }
            }
        });

    }

    private void getSearchImage(String query) {

        ApiUtilities.getApiInterface().getSearchImage(query,1,80).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                modelClasslist.clear();
                if (response.isSuccessful())
                {
                    modelClasslist.addAll(response.body().getPhotos());
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Not Able To Get", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {

            }
        });
    }

    private void findphotos() {

        modelClasslist.clear();
        ApiUtilities.getApiInterface().getImage(1,80).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {

                if (response.isSuccessful())
                {
                    modelClasslist.addAll(response.body().getPhotos());
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Not Able To Get", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {

            }
        });
    }
}