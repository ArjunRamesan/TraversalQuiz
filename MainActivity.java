package com.example.arjunramesan.traversalquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {      //created by Arjun Ramesan, Anurag Gupta, Karan Chadha on  16 October 2016//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cpuview = (Button) findViewById(R.id.button3);

        final Intent cpuv = new Intent(this,cpuActivity.class);
        cpuview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(cpuv);
            }
        });


        final Intent solr = new Intent(this,solveq.class);
        Button solvsln = (Button) findViewById(R.id.button4);
        solvsln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(solr);

            }
        });

        Button rules = (Button) findViewById(R.id.button5);
        final Intent ruls = new Intent(this, rls.class);
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(ruls);
            }
        });


    }
}
