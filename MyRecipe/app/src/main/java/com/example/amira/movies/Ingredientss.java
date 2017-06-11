package com.example.amira.movies;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Ingredientss extends ActionBarActivity {
ListView list;
    ArrayList<String> names;
    ArrayAdapter<String>adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientss);
        names=new ArrayList<>();
        names=getIntent().getExtras().getStringArrayList("name");
        list=(ListView)findViewById(R.id.list);
        adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,names);
        list.setAdapter(adapter);
    }
}
