package com.example.jimmy.a4;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.app.Activity;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.Runnable;

public class Model extends Observable {

    private static final Model ourInstance = new Model();
    static Model getInstance()
    {
        return ourInstance;
    }

    Model() {
        simon = new Simon(4);
    }

    Simon simon;

    int difficulty = 1;
    int get_difficulty(){ return difficulty; }
    void set_difficulty(final int value){
        difficulty = value;
        setChangedAndNotify();
    }

    int nbuttons = 4;
    int get_nbuttons(){ return nbuttons; }
    void set_nbuttons(final int value){
        nbuttons = value;
        simon.buttons = nbuttons;
        setChangedAndNotify();
    }

    Simon.State get_state(){
        return simon.getState();
    }

    int get_score(){
        return simon.getScore();
    }

    public boolean is_human(){
        return simon.getState() == Simon.State.HUMAN;
    }
    public boolean is_computer(){
        return simon.getState() == Simon.State.COMPUTER;
    }
    public int next;
    public boolean roundstart = false;
    public boolean newgame = true;

    void round_start() {
        if(is_computer()) return;

        newgame = false;

        simon.newRound();
        roundstart = true;
        playbuttons();
    }

    void playbuttons(){
        if(is_computer()) {
            next = simon.nextButton();
            setChangedAndNotify();
        } else {
            roundstart = false;
            setChangedAndNotify();
            return;
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                playbuttons();
                handler.removeCallbacks(this);
                return;
            }
        },(int)1200/difficulty);
    }

    public void reset(){
        roundstart = false;
        newgame = true;
        next = -1;
        simon = new Simon(nbuttons);
    }

    int is_win(){
        if(simon.getState() == Simon.State.WIN){
            return 1;
        }
        if(simon.getState() == Simon.State.LOSE){
            return 0;
        } else {
            return -1;
        }
    }

    public void click(final int button){
        simon.verifyButton(button-1);
        setChangedAndNotify();
    }

    public void setChangedAndNotify() {
        setChanged();
        notifyObservers();
    }

    @Override
    public synchronized void addObserver(Observer observer) {
        super.addObserver(observer);
    }

    @Override
    public synchronized void deleteObservers() {
        super.deleteObservers();
    }

    @Override
    public synchronized void deleteObserver(Observer o)
    {
        super.deleteObserver(o);
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }
}
