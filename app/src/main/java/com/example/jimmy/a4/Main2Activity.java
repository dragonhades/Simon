package com.example.jimmy.a4;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class Main2Activity extends AppCompatActivity implements Observer {

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    TextView instruction;
    TextView score;
    Button play;
    Button setting;

    Model model;

    Animation alpha(){
        Animation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(500);
        return animation;
    }

    Animation rotate(){
        Animation animation = new RotateAnimation(1, 3, 1, 3);
        animation.setDuration(100);
        return animation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        model = Model.getInstance();
        model.addObserver(this);

        model.reset();

        final Context context = this;

        int nbuttons = model.get_nbuttons();


        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(model.is_human()) {
                    button1.startAnimation(alpha());
                    model.click(1);
                }
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(model.is_human()){
                    button2.startAnimation(alpha());
                    model.click(2);
                }
            }
        });
        if(nbuttons < 2) button2.setVisibility(View.GONE);

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(model.is_human()) {
                    button3.startAnimation(alpha());
                    model.click(3);
                }
            }
        });
        if(nbuttons < 3) button3.setVisibility(View.GONE);

        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(model.is_human()) {
                    button4.startAnimation(alpha());
                    model.click(4);
                }
            }
        });
        if(nbuttons < 4) button4.setVisibility(View.GONE);

        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(model.is_human()) {
                    button5.startAnimation(alpha());
                    model.click(5);
                }
            }
        });
        if(nbuttons < 5) button5.setVisibility(View.GONE);

        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(model.is_human()) {
                    button6.startAnimation(alpha());
                    model.click(6);
                }
            }
        });
        if(nbuttons < 6) button6.setVisibility(View.GONE);

        instruction = (TextView) findViewById(R.id.instruction);
        instruction.setText("Press \"PLAY\" to start");

        score = (TextView) findViewById(R.id.score);
        score.setText("");

        setting = (Button) findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(context, Setting.class);
                //intent.putExtra("COUNT", model.getCounterValue());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                model.round_start();
            }
        });


        // initialize views
        model.setChangedAndNotify();
    }

    final int REQUEST_CODE = 246;

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        model.deleteObserver(this);
    }

    void shaky_animation(){
        if(!model.newgame) return;
        Random rand = new Random();
        int n = rand.nextInt(100) % model.nbuttons + 1;
        switch(n) {
            case 1:
                button1.startAnimation(rotate());
                break;
            case 2:
                button2.startAnimation(rotate());
                break;
            case 3:
                button3.startAnimation(rotate());
                break;
            case 4:
                button4.startAnimation(rotate());
                break;
            case 5:
                button5.startAnimation(rotate());
                break;
            case 6:
                button6.startAnimation(rotate());
                break;
            default:
                break;
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                shaky_animation();
                handler.removeCallbacks(this);
                return;
            }
        },100);
    }

    @Override
    public void update(Observable observable, Object data) {
        shaky_animation();
        if(model.roundstart){
            instruction.setText("Watch ...");
            score.setText("Score: "+model.get_score());
            switch(model.next+1){
                case 1:
                    button1.startAnimation(alpha());
                    break;
                case 2:
                    button2.startAnimation(alpha());
                    break;
                case 3:
                    button3.startAnimation(alpha());
                    break;
                case 4:
                    button4.startAnimation(alpha());
                    break;
                case 5:
                    button5.startAnimation(alpha());
                    break;
                case 6:
                    button6.startAnimation(alpha());
                    break;
                default:
                    break;
            }
        }
        else if(model.is_human()) {
            instruction.setText("Your turn");
            score.setText("Score: "+model.get_score());
        }
        if(model.is_win()==1){
            instruction.setText("You WIN !");
            score.setText("Score: "+model.get_score());
        }
        else if(model.is_win()==0){
            instruction.setText("You LOSE !");
            score.setText("Score: "+model.get_score()+" :(");
            model.newgame = true;
            shaky_animation();
        }
    }
}
