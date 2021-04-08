package com.example.burningseriesapp_extended.Activities;

/**
 * Created by TeaNesx 12/01/2021
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.burningseriesapp_extended.R;
import com.example.burningseriesapp_extended.ViewModel.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter adapter;
    ListView lv_SerieList;
    EditText et_filter;
    MainActivityViewModel mMainActivityViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_SerieList = findViewById(R.id.lv_Serie);
        et_filter = findViewById(R.id.et_filter);

        et_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (MainActivity.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mMainActivityViewmodel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        mMainActivityViewmodel.initSerieName(this);
        mMainActivityViewmodel.getSerieName().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> SerieName) {
                adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, SerieName);
                lv_SerieList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        mMainActivityViewmodel.initSerieUrl(this);
        mMainActivityViewmodel.getSerieURL().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> serieUrl) {
                lv_SerieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MainActivity.this, SeriesViewActivity.class);
                        intent.putExtra("MainActivitySerieUrl", serieUrl.get(i));
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
