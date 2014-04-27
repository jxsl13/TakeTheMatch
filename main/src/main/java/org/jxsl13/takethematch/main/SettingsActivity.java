package org.jxsl13.takethematch.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class SettingsActivity extends ActionBarActivity {

    Switch settingsSwitchPlayerStarts;
    Switch settingsSwitchInvertedGameplay;
    Switch settingsSwitchUseRandomizeFactor;
    Switch settingsSwitchShowDebugging;
    EditText settingsEditTextMatchesLimiter;
    EditText settingsEditTextMaxMatchesToDraw;
    RadioGroup settingsRadioGroupHardness;
    RadioButton settingsRadioButtonEasy;
    RadioButton settingsRadioButtonNormal;
    RadioButton settingsRadioButtonHard;

    private boolean settingsPlayerStarts;
    private boolean settingsInvertedGameplay;
    private boolean settingsUseRandomizeFactor;
    private int settingsMatchesLimiter;
    private int settingsMaxMatchesToDraw;
    private boolean settingsShowDebugging;
    private byte settingsHardness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        //Getting intent from MainActivity
        Intent settingsIntent = getIntent();
        settingsPlayerStarts = settingsIntent.getBooleanExtra(MainActivity.EXTRA_PLAYER_STARTS, true);
        settingsInvertedGameplay = settingsIntent.getBooleanExtra(MainActivity.EXTRA_INVERTED_GAMEPLAY, false);
        settingsUseRandomizeFactor = settingsIntent.getBooleanExtra(MainActivity.EXTRA_USE_RANDOMIZER_FACTOR, true);
        settingsMatchesLimiter = settingsIntent.getIntExtra(MainActivity.EXTRA_MATCHES_LIMIT, 20);
        settingsMaxMatchesToDraw = settingsIntent.getIntExtra(MainActivity.EXTRA_MAX_MATCHES_TO_DRAW, 3);
        settingsShowDebugging = settingsIntent.getBooleanExtra(MainActivity.EXTRA_SHOW_DEBUGGING, false);
        settingsHardness = (byte) settingsIntent.getIntExtra(MainActivity.EXTRA_HARDNESS, 3);

        //Declaring view variables
        settingsSwitchInvertedGameplay = (Switch) findViewById(R.id.switchInvertedGameplay);
        settingsSwitchPlayerStarts = (Switch) findViewById(R.id.switchPlayerStarts);
        settingsSwitchShowDebugging = (Switch) findViewById(R.id.switchDebugging);
        settingsSwitchUseRandomizeFactor = (Switch) findViewById(R.id.switchUseRandomizerFactor);
        settingsEditTextMatchesLimiter = (EditText) findViewById(R.id.editTextSettingsMatchesLimiter);
        settingsEditTextMaxMatchesToDraw = (EditText) findViewById(R.id.editTextSettingsMaxMatchesToDraw);

        settingsRadioButtonEasy = (RadioButton) findViewById(R.id.radioButtonEasy);
        settingsRadioButtonNormal = (RadioButton) findViewById(R.id.radioButtonNormal);
        settingsRadioButtonHard = (RadioButton) findViewById(R.id.radioButtonHard);
        settingsRadioGroupHardness = (RadioGroup) findViewById(R.id.radioGroupHardness);


        Runnable setViewContent = new Runnable() {
            @Override
            public void run() {
                // MORE THAN FUCKING IMPORTANT String.valueOf(""), looked half an hour for the solution xD

                settingsEditTextMatchesLimiter.setText(String.valueOf(settingsMatchesLimiter), TextView.BufferType.EDITABLE);
                settingsEditTextMaxMatchesToDraw.setText(String.valueOf(settingsMaxMatchesToDraw), TextView.BufferType.EDITABLE);

                settingsSwitchPlayerStarts.setChecked(settingsPlayerStarts);
                settingsSwitchInvertedGameplay.setChecked(settingsInvertedGameplay);
                settingsSwitchUseRandomizeFactor.setChecked(settingsUseRandomizeFactor);
                settingsSwitchShowDebugging.setChecked(settingsShowDebugging);


                switch (settingsHardness) {
                    case 1:
                        settingsRadioButtonEasy.setChecked(true);

                    case 2:
                        settingsRadioButtonNormal.setChecked(true);

                    case 3:
                        settingsRadioButtonHard.setChecked(true);
                }


            }

        };

        setViewContent.run();


        settingsSwitchUseRandomizeFactor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settingsUseRandomizeFactor = b;

            }
        });
        settingsSwitchShowDebugging.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settingsShowDebugging = b;
            }
        });
        settingsSwitchPlayerStarts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settingsPlayerStarts = b;
            }
        });
        settingsSwitchInvertedGameplay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settingsInvertedGameplay = b;
            }
        });
        settingsEditTextMaxMatchesToDraw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    settingsMaxMatchesToDraw = Integer.parseInt(charSequence.toString());
                } catch (Exception ex) {
                    settingsEditTextMaxMatchesToDraw.setText(settingsMaxMatchesToDraw, TextView.BufferType.EDITABLE);
                    Toast.makeText(getApplicationContext(), R.string.errorEnterAValidNumber, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        settingsEditTextMatchesLimiter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    settingsMatchesLimiter = Integer.parseInt(charSequence.toString());
                } catch (Exception ex) {
                    settingsEditTextMatchesLimiter.setText(settingsMatchesLimiter, TextView.BufferType.EDITABLE);
                    Toast.makeText(getApplicationContext(), R.string.errorEnterAValidNumber, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        settingsRadioGroupHardness.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (settingsRadioButtonEasy.isChecked()) {
                    settingsHardness = 1;
                }
                if (settingsRadioButtonNormal.isChecked()) {
                    settingsHardness = 2;
                }
                if (settingsRadioButtonHard.isChecked()) {
                    settingsHardness = 3;
                }
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent returnIntent = new Intent(getApplicationContext(), MainActivity.class);
        returnIntent.putExtra(MainActivity.EXTRA_MATCHES_LIMIT, settingsMatchesLimiter);
        returnIntent.putExtra(MainActivity.EXTRA_MAX_MATCHES_TO_DRAW, settingsMaxMatchesToDraw);
        returnIntent.putExtra(MainActivity.EXTRA_PLAYER_STARTS, settingsPlayerStarts);
        returnIntent.putExtra(MainActivity.EXTRA_INVERTED_GAMEPLAY, settingsInvertedGameplay);
        returnIntent.putExtra(MainActivity.EXTRA_HARDNESS, settingsHardness);
        returnIntent.putExtra(MainActivity.EXTRA_USE_RANDOMIZER_FACTOR, settingsUseRandomizeFactor);
        returnIntent.putExtra(MainActivity.EXTRA_SHOW_DEBUGGING, settingsShowDebugging);
        this.finish();
        startActivity(returnIntent);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                Intent returnIntent = new Intent(getApplicationContext(), MainActivity.class);
                returnIntent.putExtra(MainActivity.EXTRA_MATCHES_LIMIT, settingsMatchesLimiter);
                returnIntent.putExtra(MainActivity.EXTRA_MAX_MATCHES_TO_DRAW, settingsMaxMatchesToDraw);
                returnIntent.putExtra(MainActivity.EXTRA_PLAYER_STARTS, settingsPlayerStarts);
                returnIntent.putExtra(MainActivity.EXTRA_INVERTED_GAMEPLAY, settingsInvertedGameplay);
                returnIntent.putExtra(MainActivity.EXTRA_HARDNESS, settingsHardness);
                returnIntent.putExtra(MainActivity.EXTRA_USE_RANDOMIZER_FACTOR, settingsUseRandomizeFactor);
                returnIntent.putExtra(MainActivity.EXTRA_SHOW_DEBUGGING, settingsShowDebugging);
                this.finish();
                startActivity(returnIntent);

        }

        return super.onOptionsItemSelected(item);
    }
}
