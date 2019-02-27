package com.example.test.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.model.Question;
import com.example.test.model.questionBank;

import java.util.Arrays;

public class Main2Activity extends Activity implements View.OnClickListener{
    private TextView mQuestionTextView;
    private Button mAnswerButton1;
    private Button mAnswerButton2;
    private Button mAnswerButton3;
    private Button mAnswerButton4;
    private questionBank mQuestionBank;
    private Question mCurrentQuestion;
    private int mScore;
    private int mNumberofQuestions;
    public static final String BUNDLE_EXTRA_SCORE = "bundle bundle";
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";
    private boolean mEnableTouchEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mQuestionBank = this.generateQuestions();

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberofQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberofQuestions = 5;
        }

        mEnableTouchEvents = true;
        mQuestionTextView = (TextView) findViewById(R.id.game_question);
        mAnswerButton1 = (Button) findViewById(R.id.first_choice_btn);
        mAnswerButton2 = (Button) findViewById(R.id.second_choice_btn);
        mAnswerButton3 = (Button) findViewById(R.id.third_choice_btn);
        mAnswerButton4 = (Button) findViewById(R.id.fourth_choice_btn);

        mAnswerButton1.setTag(0);
        mAnswerButton2.setTag(1);
        mAnswerButton3.setTag(2);
        mAnswerButton4.setTag(3);

        mAnswerButton1.setOnClickListener(this);
        mAnswerButton2.setOnClickListener(this);
        mAnswerButton3.setOnClickListener(this);
        mAnswerButton4.setOnClickListener(this);

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_STATE_SCORE,mScore);
        outState.putInt(BUNDLE_STATE_QUESTION,mNumberofQuestions);
    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();
        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            //good answer
            Toast.makeText(this, "Good Job!!", Toast.LENGTH_SHORT).show();
            mScore++;
        }
        else {
            //false answer
            Toast.makeText(this, "Too Bad :(", Toast.LENGTH_SHORT).show();
        }
        mEnableTouchEvents = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;
                if (--mNumberofQuestions==0) {
                    //end the game
                    endGame();
                }
                else {
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                }

            }
        },2000);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Well Done")
                .setMessage("Your score is: " + mScore)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //end the game
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    private void displayQuestion(final Question question) {
        String questionValue = question.getQuestion();
        mQuestionTextView.setText(questionValue);

        mAnswerButton1.setText(question.getChoiceList().get(0));
        mAnswerButton2.setText(question.getChoiceList().get(1));
        mAnswerButton3.setText(question.getChoiceList().get(2));
        mAnswerButton4.setText(question.getChoiceList().get(3));
    }
    private questionBank generateQuestions() {
        Question question1 = new Question("What is the name of the current USA president?",
                Arrays.asList("Barack Obama", "George Bosh", "Donald Trump", "John Cena"), 2);
        Question question2 = new Question("Who holds the record for most olympic gold medals?",
                Arrays.asList("Larisa Latynina","Usain Bolt", "Mark Spitw","Michael Phelps"),3);
        Question question3 = new Question("Who is the richest man in the world?",
                Arrays.asList("Steve Jobs","Jeff Bezos","Mark Zuckerberg","Bill Gates"),1);
        Question question4 = new Question("What is the 4th closest planet to the sun in our solar system?",
                Arrays.asList("Mars","Earth","Jupiter","Mercury"),0);
        Question question5 = new Question("What does CH4 refer to in chemistry?",
                Arrays.asList("Hydrogene Chloride","Sulfuric Acid","Carbon Dioxyde","Methane"),3);
        Question question6 = new Question("How many seasons are there in a year?",
                Arrays.asList("2","3","4","5"),2);
        Question question7 = new Question("How many teams are there in the NBA league?",
                Arrays.asList("18","20","28","30"),3);
        Question question8 = new Question("What is the record for most points in a single NBA game by a single player?",
                Arrays.asList("100","92","81","79"),0);
        Question question9 = new Question("Who is the mascott of the pokemon franchise?",
                Arrays.asList("Jigglypuff","Pikachu","Charizard","Ash"),1);
        Question question10 = new Question("How many points are there in a table tennis set?",
                Arrays.asList("9","10","11","12"),2);
        return new questionBank(Arrays.asList(question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8,
                question9,
                question10));
    }

}
