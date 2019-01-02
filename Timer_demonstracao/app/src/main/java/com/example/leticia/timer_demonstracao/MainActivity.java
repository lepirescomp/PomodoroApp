package com.example.leticia.timer_demonstracao;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //global instanciate because they will be avaible for all methods,
    //pause/continue from same local,without loose teh memorie
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    SeekBar timeControl;
    TextView mostraTempo;
    Boolean counterIsActive=false;
    Button button;
    CountDownTimer countDownTimer;

    //it will get the progress and setup the countDownTimer
    int time;

    public void reseta(){
        mostraTempo.setText("0:30");
        timeControl.setProgress(30);
        countDownTimer.cancel();
        button.setText("Vai!");
        timeControl.setEnabled(true);
        counterIsActive=false;
    }

    public void atualiza(int progress){
        int minutes = (int) progress/60;
        int seconds = progress - minutes*60;

        String secondString = Integer.toString(seconds);

        if(seconds<=9){
            secondString="0"+secondString;
        }


        mostraTempo.setText(Integer.toString(minutes)+":"+secondString);
    }



    public void funcaoClique(View view){
        if(counterIsActive==false) {
            counterIsActive = true;
            timeControl.setEnabled(false);
            button.setText("Parar!");

            countDownTimer= new CountDownTimer(timeControl.getProgress() * 1000 + 100, 1000) {
                public void onTick(long millisecondsUntilDone) {

                    atualiza((int) millisecondsUntilDone / 1000);

                }

                public void onFinish() {
                    mostraTempo.setText("0:00");
                    mediaPlayer.start();
                    reseta();
                    // Counter is finished! (after 10 seconds)

//                Log.i("Done!", "Coundown Timer Finished");

                }
            }.start();
        }else{
            reseta();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);

        //starts the media with "galo" sound
        mediaPlayer = MediaPlayer.create(this, R.raw.galo);
        //get the audio manager from system  and allow make the setups as maxVolume and current
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //show the minutes
        mostraTempo = (TextView) findViewById(R.id.textView);




        //get and setup the seek bar
         timeControl = (SeekBar) findViewById(R.id.seekBar);

        timeControl.setMax(1200);
        timeControl.setProgress(30);

        timeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                atualiza(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        }

    }

