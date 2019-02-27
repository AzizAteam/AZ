package com.example.test.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.model.User;

public class MainActivity extends Activity {

    private TextView mWelcome;
    private EditText mNametype;
    private Button mButton;
    private User mUser;
    public static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    private SharedPreferences mPreferences;
    public static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";
    public static final String PREF_KEY_FIRSTNAME = "PREF_KEY_FIRSTNAME";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK==resultCode) {
            int score = data.getIntExtra(Main2Activity.BUNDLE_EXTRA_SCORE,0);

            mPreferences.edit().putInt(PREF_KEY_FIRSTNAME,score).apply();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUser = new User();
        mPreferences=getPreferences(MODE_PRIVATE);
        mWelcome = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mNametype = (EditText) findViewById(R.id.main_name_input);
        mButton = (Button) findViewById(R.id.main_button);

        mButton.setEnabled(false);
        mNametype.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mButton.setEnabled(s.toString().length() >= 4);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = mNametype.getText().toString();
                mUser.setFirstName(firstName);
                mPreferences.edit().putString(PREF_KEY_FIRSTNAME, mUser.getFirstName()).apply();
                Intent gameactivityintent = new Intent(MainActivity.this , Main2Activity.class);
                startActivityForResult(gameactivityintent, GAME_ACTIVITY_REQUEST_CODE);

            }
        });

    }
}
