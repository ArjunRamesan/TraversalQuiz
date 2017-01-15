package com.example.arjunramesan.traversalquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class givp extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_givp);

        String data = getIntent().getExtras().getString("Result");
        int score = getIntent().getExtras().getInt("Score");

        TextView sol = (TextView) findViewById(R.id.textView8);
        sol.setText(data);
        TextView scr = (TextView) findViewById(R.id.textView7);
        scr.setText("Final Score : "+score);
        final Intent newgme = new Intent(this, MainActivity.class);

        Button strtagn = (Button) findViewById(R.id.button6);
        strtagn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(newgme);
            }
        });

    }
}
