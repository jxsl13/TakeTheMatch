package org.jxsl13.takethematch.main;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private static final String EXTRA_MATCHES_LIMIT = "org.jxsl13.takethematch.main.extraMatchesLimit";
    private static final String EXTRA_MAX_MATCHES_TO_DRAW ="org.jxsl13.takethematch.main.extraMaxMatchesToDrawAtOnce" ;
    private static final String EXTRA_PLAYER_STARTS ="org.jxsl13.takethematch.main.playerStarts" ;
    private static final String EXTRA_INVERTED_GAMEPLAY ="org.jxsl13.takethematch.main.invertedGameplay" ;
    private static final String EXTRA_HARDNESS ="org.jxsl13.takethematch.main.hardness" ;
    TextView textViewMatchesLeft;
    TextView textViewYouDrew;
    TextView textViewYourOpponentDrew;
    TextView textViewDescription;
    TextView textViewGithubLink;
    Button buttonDraw;
    Button buttonRestart;
    EditText editTextMatchesToDraw;

    Runnable updateGameViews = new Runnable() {
        @Override
        public void run() {

           // if (Game.checkMatchesToDraw(draw)) {

                if (Game.getMatchesLeft() > 1 || Game.getMatchesLeft() == 0) {
                    textViewMatchesLeft.setText(Game.getMatchesLeft() + " matches left.");
                } else {
                    textViewMatchesLeft.setText(Game.getMatchesLeft() + "match left.");
                }
            if(Game.getPlayerMatchesDrawn() >0){
                textViewYouDrew.setText("You drew: " + Game.getPlayerMatchesDrawn());
            }
            else{
                textViewYouDrew.setText("");
            }
            if(Game.getAiPlayerMatchesDrawn() >0){
                textViewYourOpponentDrew.setText("Your opponent drew: " + Game.getAiPlayerMatchesDrawn());
            }
            else{
                textViewYourOpponentDrew.setText("");
            }




            //}

        }
    };


    int maxMatchesToDrawAtOnce = 3;
    int matchesLimit = 20;
    boolean playerStarts = true;
    boolean inversedGameplay = false;
    byte hardness = 3;
    private int draw;
    // Creating game engine
    // settings: maximum matches to draw at once, max matches, playerStart: whether the player or the ai starts, inverse gameplay?, hardness 1~3
    TakeTheMatchEngine Game = new TakeTheMatchEngine(maxMatchesToDrawAtOnce, matchesLimit, playerStarts, inversedGameplay, hardness);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewMatchesLeft = (TextView) findViewById(R.id.textViewMatchesLeft);
        textViewYouDrew = (TextView) findViewById(R.id.textViewYouDrew);
        textViewYourOpponentDrew = (TextView) findViewById(R.id.textViewYourOpponentDrew);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewGithubLink = (TextView) findViewById(R.id.textViewGithubLink);
        buttonDraw = (Button) findViewById(R.id.buttonDrawMatches);
        buttonRestart = (Button) findViewById(R.id.buttonRestartGame);
        buttonRestart.setVisibility(View.GONE);
        editTextMatchesToDraw = (EditText) findViewById(R.id.editTextMatchesToDraw);
        updateGameViews.run();

        Toast.makeText(getApplicationContext(), "Game started successfully!", Toast.LENGTH_SHORT).show();
        // System.out.println("New Game Created Successfully!");

        if (!inversedGameplay) {
            if (maxMatchesToDrawAtOnce > 1) {
                textViewDescription.setText("Draw up to " + maxMatchesToDrawAtOnce + " matches at once, but at least 1. The first one to reach 0 matches wins.");
                //System.out.println("Draw up to " + maxMatchesToDrawAtOnce + " matches at once, but at least 1. The first one to reach 0 matches wins.");
            } else {
                textViewDescription.setText("Draw up to " + maxMatchesToDrawAtOnce + " match at once, but at least 1. The first one to reach 0 matches wins.");
                //System.out.println("Draw up to " + maxMatchesToDrawAtOnce + " match at once, but at least 1. The first one to reach 0 matches wins.");

            }
        } else {
            if (!inversedGameplay) {
                if (maxMatchesToDrawAtOnce > 1) {
                    textViewDescription.setText("Draw up to " + maxMatchesToDrawAtOnce + " matches at once, but at least 1. The first one to reach 0 matches looses.");
                    // System.out.println("Draw up to " + maxMatchesToDrawAtOnce + " matches at once, but at least 1. The first one to reach 0 matches looses.");
                } else {
                    textViewDescription.setText("Draw up to " + maxMatchesToDrawAtOnce + " match at once, but at least 1. The first one to reach 0 matches looses.");
                    //System.out.println("Draw up to " + maxMatchesToDrawAtOnce + " match at once, but at least 1. The first one to reach 0 matches looses.");

                }
            }
        }
        if (Game.getInvertedStart()) {
            //System.out.println("Matches: " + Game.getMatchesLeft());
            Game.playInverseStart();
           // System.out.println("Your opponent drew: " + Game.getAiPlayerMatchesDrawn());
        }

       updateGameViews.run();

        buttonDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    draw = Integer.parseInt(editTextMatchesToDraw.getText().toString());
                    Game.play(draw);


                    //System.out.println("You drew: " + Game.getPlayerMatchesDrawn());
                    //System.out.println("Your opponent drew: " + Game.getAiPlayerMatchesDrawn());
                    updateGameViews.run();


                    if (!Game.getGameRunStatus()) {
                        if (Game.getPlayerWonTheGame()) {
                            Toast.makeText(getApplicationContext(), "You won!", Toast.LENGTH_LONG).show();
                            //System.out.println("You won!");
                        } else {
                            Toast.makeText(getApplicationContext(), "You lost!", Toast.LENGTH_LONG).show();
                            // System.out.println("Your opponent won!");
                        }
                        buttonRestart.setVisibility(View.VISIBLE);

                    }


                } catch (Exception ex) {
                    // System.out.println("Enter a valid number!");
                    Toast.makeText(getApplicationContext(), "Enter a valid number!", Toast.LENGTH_SHORT).show();
                }

            }


        });

        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game.RestartGame(maxMatchesToDrawAtOnce, matchesLimit, playerStarts, inversedGameplay, hardness);
                updateGameViews.run();
            }
        });


        textViewGithubLink.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard;
                clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("github.com/jxsl13/TakeTheMatch", "https://github.com/jxsl13/TakeTheMatch/");
                clipboard.setPrimaryClip(clip);
            }
        });


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
            Intent intentSettings = new Intent(getApplicationContext(), SettingsActivity.class);
            intentSettings.putExtra(EXTRA_MATCHES_LIMIT, matchesLimit);
            intentSettings.putExtra(EXTRA_MAX_MATCHES_TO_DRAW, maxMatchesToDrawAtOnce);
            intentSettings.putExtra(EXTRA_PLAYER_STARTS, playerStarts);
            intentSettings.putExtra(EXTRA_INVERTED_GAMEPLAY, inversedGameplay);
            intentSettings.putExtra(EXTRA_HARDNESS, hardness);
            startActivity(intentSettings);
        }
        return super.onOptionsItemSelected(item);
    }

}
