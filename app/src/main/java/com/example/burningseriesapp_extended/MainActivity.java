package com.example.burningseriesapp_extended;

/**
 * Created by TeaNesx 12/01/2021
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD:app/src/main/java/com/example/burningseriesapp_extended/Activities/MainActivity.java
=======
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
>>>>>>> parent of 3b8b57a (Getting Url from repository and set click event):app/src/main/java/com/example/burningseriesapp_extended/MainActivity.java
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.burningseriesapp_extended.ViewModel.MainActivityViewModel;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    ArrayList<String> SerieList = new ArrayList<>();
    ArrayList<String> LinkList = new ArrayList<>();
    MainActivityViewModel mMainActivityViewmodel;

    ArrayAdapter adapter;
    private ListView lv_SerieList;
    private EditText et_filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_SerieList = findViewById(R.id.lv_Serie);
        et_filter = findViewById(R.id.et_filter);

        /*
        (new Thread(new Runnable() {
            @Override
            public void run() {
                // Background executed code (Lambda-Expression)
                Ion.with(getApplicationContext()).load("https://burning-series.io/andere-serien").asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        //Pattern and matcher to fetch title
                        Pattern pattern = Pattern.compile("<li><a href=\".+?\" title=\".+?\">(.+?)</a></li>", Pattern.DOTALL);
                        Matcher matcher = pattern.matcher(result);

                        while (matcher.find()) {
                            SerieList.add(matcher.group(1));
                        }

                        Pattern LinkPattern = Pattern.compile("<li><a href=\"(.+?)\" title=\".+?\">.+?</a></li>", Pattern.DOTALL);
                        Matcher LinkMatcher = LinkPattern.matcher(result);

                        while (LinkMatcher.find()) {
                            LinkList.add(LinkMatcher.group(1));
                        }

                        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, SerieList);
                        lv_SerieList.setAdapter(adapter);

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

                        lv_SerieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String selectedItem = lv_SerieList.getItemAtPosition(i).toString().trim();
                                selectedItem = selectedItem.replaceAll("\\W+","-").replaceAll("^\\W*(.+?)\\W*$","$1");
                                Intent intent = new Intent(MainActivity.this, SeriesViewActivity.class);
                                intent.putExtra("selectedItem", selectedItem);
                                startActivity(intent);
                            }
                        });

                    }
                });
            }
        })).start();
         */
        mMainActivityViewmodel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mMainActivityViewmodel.init();
        
        mMainActivityViewmodel.getSerie(this).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> SerieName) {
                adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, SerieName);
                lv_SerieList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        mMainActivityViewmodel.getSerieUrl(this).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> serieUrl) {
                lv_SerieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MainActivity.this, SeriesViewActivity.class);
                        intent.putExtra("MainActivitySerieUrl", serieUrl.get(i));
                        MainActivity.this.startActivity(intent);
                    }
                });
            }
        });
    }
}
