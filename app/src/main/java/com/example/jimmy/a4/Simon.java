package com.example.jimmy.a4;

import java.util.ArrayList;
import java.util.Random;

public class Simon {

    public enum State { START, COMPUTER, HUMAN, LOSE, WIN };

    // the game state and score
    State state;
    int score;

    // length of sequence
    int length;
    // number of possible buttons
    int buttons;

    // the sequence of buttons and current button
    ArrayList<Integer> sequence = new ArrayList<>();
    int index;

    boolean debug;

    public Simon(int _buttons) { init(_buttons, false); }

    public Simon(int _buttons, boolean _debug) { init(_buttons, _debug); }

    void init(int _buttons, boolean _debug){

        // true will output additional information
        // (you will want to turn this off before)
        debug = _debug;

        length = 1;
        buttons = _buttons;
        state = State.START;
        score = 0;

        //if (debug) { cout << "[DEBUG] starting " << buttons << " button game" << endl; }
    }

    public int getNumButtons() { return buttons; }

    public int getScore() { return score; }

    public State getState() { return state; }

    public String getStateAsString() {

        switch (getState()) {
            case START:
                return "START";
            case COMPUTER:
                return "COMPUTER";
            case HUMAN:
                return "HUMAN";
            case LOSE:
                return "LOSE";
            case WIN:
                return "WIN";
            default:
                return "Unknown State";
        }
    }

    public void newRound() {

//        if (debug) {
//            cout << "[DEBUG] newRound, Simon::state "
//                    << getStateAsString() << endl;
//        }

        // reset if they lost last time
        if (state == State.LOSE) {
            //if (debug) { cout << "[DEBUG] reset length and score after loss" << endl; }
            length = 1;
            score = 0;
        }

        sequence.clear();

        //if (debug) { cout << "[DEBUG] new sequence: "; }

        for (int i = 0; i < length; i++) {
            Random rand = new Random();
            int b = rand.nextInt(100) % buttons;
            sequence.add(b);
            //if (debug) { cout << b << " "; }
        }
        //if (debug) { cout << endl; }

        index = 0;
        state = State.COMPUTER;

    }

    // call this to get next button to show when computer is playing
    public int nextButton() {

        if (state != State.COMPUTER) {
//            cout << "[WARNING] nextButton called in "
//                    << getStateAsString() << endl;
            return -1;
        }

        // this is the next button to show in the sequence
        int button = sequence.get(index);

//        if (debug) {
//            cout << "[DEBUG] nextButton:  index " << index
//                    << " button " << button
//                    << endl;
//        }

        // advance to next button
        index++;

        // if all the buttons were shown, give
        // the human a chance to guess the sequence
        if (index >= sequence.size()) {
            index = 0;
            state = State.HUMAN;
        }

        return button;
    }

    public boolean verifyButton(int button) {

        if (state != State.HUMAN) {
//            cout << "[WARNING] verifyButton called in "
//                    << getStateAsString() << endl;
            return false;
        }

        // did they press the right button?
        boolean correct = (button == sequence.get(index));

//        if (debug) {
//            cout << "[DEBUG] verifyButton: index " << index
//                    << ", pushed " << button
//                    << ", sequence " << sequence[index];
//        }

        // advance to next button
        index++;

        // pushed the wrong buttons
        if (!correct) {
            state = State.LOSE;
//            if (debug) {
//                cout << ", wrong. " << endl;
//                cout << "[DEBUG] state is now " << getStateAsString() << endl;
//            }

            // they got it right
        } else {
//            if (debug) { cout << ", correct." << endl; }

            // if last button, then the win the round
            if (index == sequence.size()) {
                state = State.WIN;
                // update the score and increase the difficulty
                score++;
                length++;

//                if (debug) {
//                    cout << "[DEBUG] state is now " << getStateAsString() << endl;
//                    cout << "[DEBUG] new score " << score
//                            << ", length increased to " << length
//                            << endl;
//                }
            }
        }
        return correct;
    }

};

