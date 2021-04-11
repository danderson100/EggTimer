package com.davidapps.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView countdownText;
    Button stopButton;
    Button startButton;
    CountDownTimer timer;
    SeekBar seekBar;

    //TODO: Gray out seekbar and make it move with the timer's progress
    //TODO: Add a sound to play at the end of the timer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countdownText = findViewById(R.id.countdownText);
        stopButton = findViewById(R.id.stopButton);
        startButton = findViewById(R.id.startButton);
        seekBar = findViewById(R.id.seekBar);
    }

    /**
     * Purpose: ADD INFO HERE
     *
     * @param view
     */
    public void startCountdown(View view) {

        startButton.setVisibility(View.GONE);
        stopButton.setVisibility(View.VISIBLE);
        seekBar.setEnabled(false);

        int seconds = getSeekBarProgress();
        int millis = seconds * 1000;

        timer = new CountDownTimer(millis, 1000) {
            //FIXME: The triple if/else is awkward
            public void onTick(long millisecondsUntilDone) {
                long secondsLeft = millisecondsUntilDone / 1000;
                String timerText;
                if (secondsLeft > 59) {
                    timerText = getMinutesAndSeconds(secondsLeft);
                    countdownText.setText(timerText);
                } else {
                    timerText = String.valueOf(secondsLeft);

                    if (secondsLeft < 10) {
                        countdownText.setText(String.format("0:0%s", timerText));
                    } else {
                        countdownText.setText(String.format("0:%s", timerText));
                    }

                }

            }

            public void onFinish() {
                Log.i("All done. Play the sound!", "No more countdown");
                MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.bell);
                mediaPlayer.start();
            }
        }.start();
    }

    /**
     * Purpose: Helper method that converts the seconds to minutes/seconds format
     * (M:SS) when the egg timer is longer than 59 seconds.
     *
     * @param secondsLeft is the number of seconds in the timer
     * @return a string with minutes and seconds (M:SS) format
     */
    private String getMinutesAndSeconds(long secondsLeft) {

        int numMinutes = (int) secondsLeft / 60;
        int secondsRemaining = (int) secondsLeft % 60;

        String minsAsString = String.valueOf(numMinutes);
        String secsAsString = String.valueOf(secondsRemaining);

        StringBuilder minsSecsString = new StringBuilder();

        if (secondsRemaining < 10) {
            minsSecsString.append(minsAsString).append(":0").append(secsAsString);
        } else {
            minsSecsString.append(minsAsString).append(":").append(secsAsString);
        }

        return minsSecsString.toString();
    }

    @SuppressLint("SetTextI18n")
    public void stopCountdown(View view) {
        resetDefaults();
        timer.cancel();
        Log.i("stop countdown", "making stop button disappear...");
        stopButton.setVisibility(View.GONE);
        startButton.setVisibility(View.VISIBLE);
    }

    private void resetDefaults() {
        seekBar.setEnabled(true);
        seekBar.setProgress(30);
        countdownText.setText(R.string.default_time);
    }

    private int getSeekBarProgress() {
        return seekBar.getProgress();
    }


}