package com.aot.devsks.dice;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class homeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       FloatingActionButton  start = (FloatingActionButton) findViewById(R.id.startbutton);
        start.setOnClickListener(this);
       FloatingActionButton end = (FloatingActionButton) findViewById(R.id.exitbutton);
        end.setOnClickListener(this);

    }


    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.startbutton:
                                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(i);
                                    break;
            case R.id.exitbutton:
                                    finish();
                                    break;
            default:
                        Toast.makeText(this,"dsds",Toast.LENGTH_SHORT).show();
        }

    }
}
