package com.example.amira.movies;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

public class instructions extends ActionBarActivity {
String instruct;
    TextView instructionss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insructions);
        instruct=getIntent().getExtras().getString("inst");
        instructionss=(TextView)findViewById(R.id.tv_instructions);
        instructionss.setText("   "+instruct);
    }
}
