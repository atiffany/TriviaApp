package com.abbytiffany.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int score = 0;
    int total = 6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        Spinner spinner = (Spinner) findViewById(R.id.question_5_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.answers_5_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        EditText text = (EditText) findViewById(R.id.answer_3);
        text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(view);
                }
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void updateScore() {
        score++;
    }

    public void onRadioButtonClicked(int resource) {
        RadioButton radio = (RadioButton) findViewById(resource);
        boolean checked = radio.isChecked();
        switch (resource) {
            case R.id.correct_answer_1:
            case R.id.correct_answer_4:
                if (checked) {
                    updateScore();
                    break;
                }
                else break;
            default:
                break;
        }
    }


    public void onCheckBoxClicked(int resource) {
        CheckBox check = (CheckBox) findViewById(resource);
        boolean checked = check.isChecked();
        switch (resource) {
            case R.id.correct_answer_2_a:
            case R.id.correct_answer_2_b:
                if (checked) {
                    updateScore();
                    break;
                }
                else break;
            default:
                break;
        }
    }

    public void checkIfTextIsCorrect() {
        String correctAnswer = getString(R.string.answer_3).toLowerCase();
        EditText mEdit = (EditText)findViewById(R.id.answer_3);
        String entered = mEdit.getText().toString().toLowerCase();
        if (entered.equals(correctAnswer)) {
            updateScore();
        }
    }

    private void checkIfSpinnerIsCorrect(int resource) {
        String correctAnswer = getString(R.string.answer_5);
        Spinner spinner = (Spinner) findViewById(resource);
        String entered = spinner.getSelectedItem().toString();
        if (entered.equals(correctAnswer)) {
            updateScore();
        }
    }

    private void reset(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void displayFinalScore(View view) {
        onRadioButtonClicked(R.id.correct_answer_1);
        onCheckBoxClicked(R.id.correct_answer_2_a);
        onCheckBoxClicked(R.id.correct_answer_2_b);
        checkIfTextIsCorrect();
        onRadioButtonClicked(R.id.correct_answer_4);
        checkIfSpinnerIsCorrect(R.id.question_5_spinner);

        String answer = getString(R.string.your_score_is) + " : " + score + "/" + total;
        Toast endScore = Toast.makeText(getBaseContext(), answer, Toast.LENGTH_LONG);
        endScore.show();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                reset();
            }
        }, 1000);
    }
}
