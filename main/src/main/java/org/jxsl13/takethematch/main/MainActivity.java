package org.jxsl13.takethematch.main;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity {

    TextView textViewMatchesLeft;
    TextView textViewYouDrew;
    TextView textViewYourOpponentDrew;
    Button buttonDraw;
    Button buttonRestart;
    EditText editTextMatchesToDraw;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewMatchesLeft = (TextView) findViewById(R.id.textViewMatchesLeft);
        textViewYouDrew =(TextView) findViewById(R.id.textViewYouDrew);
        textViewYourOpponentDrew = (TextView) findViewById(R.id.textViewYourOpponentDrew);
        buttonDraw = (Button) findViewById(R.id.buttonDrawMatches);
        buttonRestart =(Button) findViewById(R.id.buttonRestartGame);
        editTextMatchesToDraw = (EditText) findViewById(R.id.editTextMatchesToDraw);


        // Creating game engine
        // settings: maximum matches to draw at once, max matches, playerStart: whether the player or the ai starts, inverse gameplay?, hardness 1~3
        //
        //
        int maxMatchesToDrawAtOnce = 3;
        int matchesLimit = 20;
        boolean playStarts = true;
        boolean inversedGameplay = false;
        byte hardness = 3;

        TakeTheMatchEngine Game = new TakeTheMatchEngine(maxMatchesToDrawAtOnce, matchesLimit, playStarts, inversedGameplay, hardness);
        System.out.println("New Game Created Successfully!");

        if (inversedGameplay == false) {
            if (maxMatchesToDrawAtOnce > 1) {
                System.out.println("Draw up to " + maxMatchesToDrawAtOnce + " matches at once, but at least 1. The first one to reach 0 matches wins.");
            } else {
                System.out.println("Draw up to " + maxMatchesToDrawAtOnce + " match at once, but at least 1. The first one to reach 0 matches wins.");

            }
        } else {
            if (inversedGameplay == false) {
                if (maxMatchesToDrawAtOnce > 1) {
                    System.out.println("Draw up to " + maxMatchesToDrawAtOnce + " matches at once, but at least 1. The first one to reach 0 matches looses.");
                } else {
                    System.out.println("Draw up to " + maxMatchesToDrawAtOnce + " match at once, but at least 1. The first one to reach 0 matches looses.");

                }
            }
        }
        if (Game.getInvertedStart()) {
            System.out.println("Matches: " + Game.getMatchesLeft());
            Game.playInverseStart();
            System.out.println("Your opponent drew: " + Game.getAiPlayerMatchesDrawn());
        }

        while (Game.getGameRunStatus()) {
            System.out.println("Matches left: " + Game.getMatchesLeft());
            try {
               // int draw = IO.eingabeInteger("Input the number of matches you want to draw:");
              //  Game.play(draw);
               // if (Game.checkMatchesToDraw(draw)) {

                    System.out.println("You drew: " + Game.getPlayerMatchesDrawn());
                    System.out.println("Your opponent drew: " + Game.getAiPlayerMatchesDrawn());

               // }

                if (!Game.getGameRunStatus()) {
                    if (Game.getPlayerWonTheGame()) {
                        System.out.println("You won!");
                    } else {
                        System.out.println("Your opponent won!");
                    }
                    break;
                }

            } catch (Exception ex) {
                System.out.println("Enter a valid number!");
            }

        }





















    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
