package org.jxsl13.takethematch.main;

import android.content.Intent;
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

    public static final String EXTRA_MATCHES_LIMIT = "org.jxsl13.takethematch.main.extraMatchesLimit";
    public static final String EXTRA_MAX_MATCHES_TO_DRAW = "org.jxsl13.takethematch.main.extraMaxMatchesToDrawAtOnce";
    public static final String EXTRA_PLAYER_STARTS = "org.jxsl13.takethematch.main.playerStarts";
    public static final String EXTRA_INVERTED_GAMEPLAY = "org.jxsl13.takethematch.main.invertedGameplay";
    public static final String EXTRA_HARDNESS = "org.jxsl13.takethematch.main.hardness";
    public static final String EXTRA_USE_RANDOMIZER_FACTOR = "org.jxsl13.takethematch.main.useRandomizerFactor";
    public static final String EXTRA_SHOW_DEBUGGING = "org.jxsl13.takethematch.main.Debugging";

    TextView textViewMatchesLeft;
    TextView textViewYouDrew;
    TextView textViewYourOpponentDrew;
    TextView textViewDescription;
    TextView textViewGithubLink;
    Button buttonDraw;
    Button buttonRestart;
    EditText editTextMatchesToDraw;
    TextView textViewDebugging;

    Runnable updateGameViews = new Runnable() {
        @Override
        public void run() {


            if (Game.getMatchesLeft() > 1 || Game.getMatchesLeft() == 0) {
                textViewMatchesLeft.setText(Game.getMatchesLeft() + " matches left.");
            } else {
                textViewMatchesLeft.setText(Game.getMatchesLeft() + " match left.");
            }
            if (Game.getPlayerMatchesDrawn() > 0) {
                textViewYouDrew.setText("You drew: " + Game.getPlayerMatchesDrawn());
            } else {
                textViewYouDrew.setText("");
            }
            if (Game.getAiPlayerMatchesDrawn() > 0) {
                textViewYourOpponentDrew.setText("Your opponent drew: " + Game.getAiPlayerMatchesDrawn());
            } else {
                textViewYourOpponentDrew.setText("");
            }

            if (debugging) {
                textViewDebugging.setText(Game.getDebugginInfo());
                textViewDebugging.setVisibility(View.VISIBLE);
            } else {
                textViewDebugging.setVisibility(View.GONE);
            }


        }
    };

    private int draw;


    int maxMatchesToDrawAtOnce;
    int matchesLimit;
    boolean playerStarts;
    boolean inversedGameplay;
    byte hardness = 3;
    boolean debugging;
    boolean useRandomizerFactor;
    // Creating game engine
    // settings: maximum matches to draw at once, max matches, playerStart: whether the player or the ai starts, inverse gameplay?, hardness 1~3
    TakeTheMatchEngine Game;

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
        textViewDebugging = (TextView) findViewById(R.id.textViewDebugging);

        Intent getSettingsIntent = getIntent();

        maxMatchesToDrawAtOnce = getSettingsIntent.getIntExtra(EXTRA_MAX_MATCHES_TO_DRAW, 3);
        matchesLimit = getSettingsIntent.getIntExtra(EXTRA_MATCHES_LIMIT, 20);
        playerStarts = getSettingsIntent.getBooleanExtra(EXTRA_PLAYER_STARTS, true);
        inversedGameplay = getSettingsIntent.getBooleanExtra(EXTRA_INVERTED_GAMEPLAY, false);
        hardness = (byte) getSettingsIntent.getIntExtra(EXTRA_HARDNESS, 3);
        debugging = getSettingsIntent.getBooleanExtra(EXTRA_SHOW_DEBUGGING, false);
        useRandomizerFactor = getSettingsIntent.getBooleanExtra(EXTRA_USE_RANDOMIZER_FACTOR, true);


        Game = new TakeTheMatchEngine(maxMatchesToDrawAtOnce, matchesLimit, playerStarts, inversedGameplay, hardness, useRandomizerFactor);


        Toast.makeText(getApplicationContext(), "Game started successfully!", Toast.LENGTH_SHORT).show();

        if (Game.getInvertedStart()) {

            Game.playInverseStart();
        }

        if (!inversedGameplay) {
            if (maxMatchesToDrawAtOnce > 1) {
                textViewDescription.setText("Draw up to " + maxMatchesToDrawAtOnce + " matches at once, but at least 1. The first one to reach 0 matches wins.");
                //System.out.println("Draw up to " + maxMatchesToDrawAtOnce + " matches at once, but at least 1. The first one to reach 0 matches wins.");
            } else {
                textViewDescription.setText("Draw up to " + maxMatchesToDrawAtOnce + " match at once, but at least 1. The first one to reach 0 matches wins.");
                //System.out.println("Draw up to " + maxMatchesToDrawAtOnce + " match at once, but at least 1. The first one to reach 0 matches wins.");

            }
        } else {
            if (inversedGameplay) {
                if (maxMatchesToDrawAtOnce > 1) {
                    textViewDescription.setText("Draw up to " + maxMatchesToDrawAtOnce + " matches at once, but at least 1. The first one to reach 0 matches looses.");
                    // System.out.println("Draw up to " + maxMatchesToDrawAtOnce + " matches at once, but at least 1. The first one to reach 0 matches looses.");
                } else {
                    textViewDescription.setText("Draw up to " + maxMatchesToDrawAtOnce + " match at once, but at least 1. The first one to reach 0 matches looses.");
                    //System.out.println("Draw up to " + maxMatchesToDrawAtOnce + " match at once, but at least 1. The first one to reach 0 matches looses.");

                }
            }
        }


        updateGameViews.run();

        buttonDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    draw = Integer.parseInt(editTextMatchesToDraw.getText().toString());
                    Game.play(draw);


                    if (!Game.getGameRunStatus()) {
                        if (Game.getPlayerWonTheGame()) {
                            Toast.makeText(getApplicationContext(), "You won!", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "You lost!", Toast.LENGTH_LONG).show();

                        }
                        buttonRestart.setVisibility(View.VISIBLE);

                    }
                    updateGameViews.run();


                } catch (Exception ex) {

                    Toast.makeText(getApplicationContext(), getString(R.string.errorEnterAValidNumber), Toast.LENGTH_SHORT).show();
                }

            }


        });

        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game.RestartGame(maxMatchesToDrawAtOnce, matchesLimit, playerStarts, inversedGameplay, hardness, useRandomizerFactor);
                updateGameViews.run();
                buttonRestart.setVisibility(View.GONE);
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

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intentSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                intentSettings.putExtra(EXTRA_MATCHES_LIMIT, matchesLimit);
                intentSettings.putExtra(EXTRA_MAX_MATCHES_TO_DRAW, maxMatchesToDrawAtOnce);
                intentSettings.putExtra(EXTRA_PLAYER_STARTS, playerStarts);
                intentSettings.putExtra(EXTRA_INVERTED_GAMEPLAY, inversedGameplay);
                intentSettings.putExtra(EXTRA_HARDNESS, hardness);
                intentSettings.putExtra(EXTRA_USE_RANDOMIZER_FACTOR, useRandomizerFactor);
                intentSettings.putExtra(EXTRA_SHOW_DEBUGGING, debugging);
                startActivity(intentSettings);
                this.finish();

                return true;
            case R.id.actoin_restartGame:
                buttonRestart.callOnClick();
                // Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
                //Game.RestartGame(maxMatchesToDrawAtOnce, matchesLimit, playerStarts, inversedGameplay, hardness, useRandomizerFactor);
                // updateGameViews.run();
                // buttonRestart.setVisibility(View.GONE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
