package com.example.jimmy.a4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = this;

        Button start_button = (Button) findViewById(R.id.start_button);
        start_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(context, Main2Activity.class);
                //intent.putExtra("COUNT", model.getCounterValue());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }
    final int REQUEST_CODE = 123;
}
