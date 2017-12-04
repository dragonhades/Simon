package com.example.jimmy.a4;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Observable;
import java.util.Observer;

public class Setting extends AppCompatActivity implements Observer {

    Model model;

    TextView diff;
    TextView nbuttons;
    SeekBar diff_bar;
    SeekBar nbuttons_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        model = Model.getInstance();
        model.addObserver(this);

        final Context context = this;

        final Button done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(context, Main2Activity.class);
                //intent.putExtra("COUNT", model.getCounterValue());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        diff = (TextView) findViewById(R.id.difficulty);
        diff.setText(String.valueOf(model.get_difficulty()));
        nbuttons = (TextView) findViewById(R.id.button_num);
        nbuttons.setText(String.valueOf(model.get_nbuttons()));

        diff_bar = (SeekBar) findViewById(R.id.diff_bar);
        diff_bar.setMax(2);
        diff_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                model.set_difficulty(progress+1);
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
        });
        diff_bar.setProgress(model.get_difficulty()-1);

        nbuttons_bar = (SeekBar) findViewById(R.id.button_bar);
        nbuttons_bar.setMax(5);
        nbuttons_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                model.set_nbuttons(progress+1);
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
        });
        nbuttons_bar.setProgress(model.get_nbuttons()-1);

        //model.setChangedAndNotify();
    }

    final int REQUEST_CODE = 350;


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //model.reset();
        
        model.deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        switch(model.get_difficulty()){
            case 1:
                diff.setText("CS136");
                break;
            case 2:
                diff.setText("CS246");
                break;
            case 3:
                diff.setText("CS350");
                break;
        }
        nbuttons.setText(String.valueOf(model.get_nbuttons()));
    }
}
