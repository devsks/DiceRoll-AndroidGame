package com.aot.devsks.dice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import  android.os.Handler;
import java.util.Random;

public class MainActivity extends AppCompatActivity{
    private ImageView diceImage;
    private Button rollButton;
    private Button holdButton;
    private Button resetButton;
    private TextView topMessage;
    private TextView userCurrentText;
    private TextView andiCurrentText;
    private TextView userTotalText;
    private TextView andiTotalText;
    private int userScore;
    private int userCurrentScore;
    private int andiScore;
    private int andiCurrentScore;
    private int diceNumber;
    private int whosTurn;
    private Random r;
    Handler time = new Handler();

    Runnable run = new Runnable() {
        @Override
        public void run() {
            andiPlaying();
            time.postDelayed(run,1500);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        diceImage = (ImageView) findViewById(R.id.diceImage);
        rollButton = (Button) findViewById(R.id.roll);
        holdButton = (Button) findViewById(R.id.hold);
        resetButton = (Button) findViewById(R.id.reset);
        topMessage = (TextView) findViewById(R.id.topMsg);
        userCurrentText = (TextView) findViewById(R.id.userCurrent);
        andiCurrentText = (TextView) findViewById(R.id.andiCurrent);
        userTotalText = (TextView) findViewById(R.id.userTotal);
        andiTotalText = (TextView) findViewById(R.id.andyTotal);
        r = new Random();
        userScore = savedInstanceState !=null ? savedInstanceState.getInt("userScore") : 0;
        andiScore = savedInstanceState !=null ? savedInstanceState.getInt("andiScore") : 0;
        userCurrentScore =  savedInstanceState !=null ?savedInstanceState.getInt("userCurrentScore") : 0;
        andiCurrentScore =  savedInstanceState !=null ?savedInstanceState.getInt("userScore") : 0;
        diceNumber =  savedInstanceState !=null ?savedInstanceState.getInt("diceNumber") : 7;
        whosTurn =  savedInstanceState !=null ?savedInstanceState.getInt("whosTurn") :0;
        updateTotalView();
        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                stop_timer();
                userScore = andiScore = 0;
                userCurrentScore = andiCurrentScore = 0;
                diceNumber =  7;
                whosTurn = 0;
                rollButton.setVisibility(View.VISIBLE);
                holdButton.setVisibility(View.VISIBLE);
                rollButton.setEnabled(true);
                holdButton.setEnabled(true);
                updateTotalView();
            }
        }
        );
        holdButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                userScore += userCurrentScore;
                userCurrentScore  = 0;
                diceNumber =  7;
                updateTotalView();
                start_timer();
            }
        });
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPlay();
            }
        });
    }

    @Override
    public  void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("userScore", userScore);
        savedInstanceState.putInt("andiScore", andiScore);
        savedInstanceState.putInt("userCurrentScore", userCurrentScore);
        savedInstanceState.putInt("andiCurrentScore", andiCurrentScore);
        savedInstanceState.putInt("diceNumber", diceNumber);
        savedInstanceState.putInt("whosTurn", whosTurn);
        super.onSaveInstanceState(savedInstanceState);
    }
    void userPlay() {
        diceRolling();
        if(diceNumber == 1)
        {
            userCurrentScore = 0;
            updateTotalView();
            Toast.makeText(MainActivity.this,"Andi will play",Toast.LENGTH_SHORT).show();
            start_timer();
        }
        else
            userCurrentScore += diceNumber;
        updateTotalView();
    }
    public void diceRolling(){
            diceNumber = r.nextInt(6)+1;
            int fileIndex = 0;
            switch(diceNumber) {
            case 1: fileIndex = R.drawable.dice1;break;
            case 2: fileIndex = R.drawable.dice2;break;
            case 3: fileIndex = R.drawable.dice3;break;
            case 4: fileIndex = R.drawable.dice4;break;
            case 5: fileIndex = R.drawable.dice5;break;
            case 6: fileIndex = R.drawable.dice5;break;
            default:fileIndex = R.drawable.dicerolling;
            }
            diceImage.setImageResource(fileIndex);

    }
    void andiPlaying() {
                diceRolling();
                if (diceNumber == 1) {
                    diceNumber = 7;
                    andiCurrentScore = 0;
                    updateTotalView();
                    stop_timer();

                } else {

                    andiCurrentScore += diceNumber;
                    updateTotalView();
                    if (andiCurrentScore > 15)
                    {

                        stop_timer();
                        diceNumber = 7;
                        andiScore += andiCurrentScore;
                        andiCurrentScore = 0;
                        rollButton.setEnabled(true);
                        holdButton.setEnabled(true);
                        updateTotalView();

                    }
                }
    }
    void updateTotalView() {


        userCurrentText.setText("Your Current Score : " + userCurrentScore );
        andiCurrentText.setText("Andy's Current Score : " + andiCurrentScore);
        userTotalText.setText("User Total Score : " + userScore);
        andiTotalText.setText("Andy Total Score : " + andiScore);
        if(userScore >=50 || andiScore >=50)
        {
            stop_timer();
            rollButton.setVisibility(View.INVISIBLE);
            holdButton.setVisibility(View.INVISIBLE);
            topMessage.setText( userScore >=50 ?"You Have Won" : "Andy Has Won!! YOU LOST");
        }
        else if(userScore > andiScore)
            topMessage.setText("Your are Leading !!");
        else if (andiScore > userScore)
            topMessage.setText("Andy is leading, Hurry UP !!");
        else
            topMessage.setText("Roll the Dice");
    }
    void start_timer()
    {
        rollButton.setEnabled(false);
        holdButton.setEnabled(false);
        time.postDelayed(run, 1000);

    }
    void stop_timer()
    {

        new Thread(new Runnable() {
            public void run() {
                time.removeCallbacks(run);
            }
        }).start();
        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
    }

}