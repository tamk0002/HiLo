/**
 * HiLo GAME
 * To implement, test and deploy a guess a number Android app.
 * Players gets 10 chances to guess the randomly generated number.
 * If they fail, a PLEASE RESET toast message will pop up,
 * if their guess it within 5 guesses, they get a SUPERIOR WIN toast message,
 * if their guess is within 10, they get an EXCELLENT WIN toast message.
 * Players may view the answer by pressing and holding the RESET button.
 * To restart, press the RESET button.
 *
 * @author Christine Tamkican (tamk0002@algonquinlive.com)
 */
package com.algonquincollege.tamk0002.hilo;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity {

    int randomNumber;
    int userGuess;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random randomGenerator = new Random();
        randomNumber = randomGenerator.nextInt(1000 - 1) + 1;

        Button guessBtn = findViewById(R.id.guess);
        guessBtn.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        view.setAlpha(0.5f);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        view.setAlpha(1f);
                        break;
                    }
                }
                return false;
            }
        });

        guessBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                count++;

                Button guessBtn = findViewById(R.id.guess);

                String pleaseReset = getString(R.string.pleaseReset);
                String exWin = getString(R.string.exWin);
                String supWin = getString(R.string.supWin);
                String low = getString(R.string.low);
                String high = getString(R.string.high);
                String guessNum = getString(R.string.guessNum);

                EditText userInput = findViewById(R.id.userInput);

                try {
                    userGuess = Integer.parseInt(userInput.getText().toString());
                } catch (NumberFormatException nfe) {
                    userInput.setError("Invalid Input");
                    userInput.requestFocus();
                    return;
                }
                if ((userGuess > 1000) || (userGuess == 0)) {
                    userInput.setError("Invalid Number");
                    userInput.requestFocus();
                    return;
                }

                if (userGuess == randomNumber) {
                    guessBtn.setEnabled(false);
                    guessBtn.setAlpha(0.5f);
                    if ((count > 5) && (count <= 10)) {
                        Toast.makeText(getApplicationContext(), exWin + ' ' + count, Toast.LENGTH_LONG).show();
                    }
                    if (count <= 5) {
                        Toast.makeText(getApplicationContext(), supWin + ' ' + count, Toast.LENGTH_LONG).show();
                    }
                } else if (userGuess < randomNumber) {
                    if (count == 10) {
                        guessBtn.setEnabled(false);
                        guessBtn.setAlpha(0.5f);
                        Toast.makeText(getApplicationContext(), pleaseReset, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), userGuess + " " + low + guessNum + ' ' + count, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (count == 10) {
                        guessBtn.setEnabled(false);
                        guessBtn.setAlpha(0.5f);
                        Toast.makeText(getApplicationContext(), pleaseReset, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), userGuess + " " + high + guessNum + ' ' + count, Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });

        Button resetBtn = findViewById(R.id.reset);

        resetBtn.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        view.setAlpha(0.5f);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        view.setAlpha(1f);
                        break;
                    }
                }
                return false;
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText userInput = findViewById(R.id.userInput);
                String resetComp = getString(R.string.resetComp);

                Button guessBtn = findViewById(R.id.guess);
                guessBtn.setEnabled(true);
                guessBtn.setAlpha(1f);

                Random randomGenerator = new Random();
                randomNumber = randomGenerator.nextInt(1000);
                System.out.print(randomNumber);
                userInput.setText("");
                count = 0;
                Toast.makeText(getApplicationContext(), resetComp, Toast.LENGTH_SHORT).show();
            }

        });

        resetBtn.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {

                Button guessBtn = findViewById(R.id.guess);
                guessBtn.setEnabled(false);
                guessBtn.setAlpha(0.5f);

                String ans = getString(R.string.ans);
                Toast.makeText(getApplicationContext(), ans + ' ' + randomNumber, Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static final String ABOUT_DIALOG_TAG = "About Dialog";

}
