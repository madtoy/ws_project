package hu.uniobuda.nik.weathergame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;


public class OptionsActivity extends AppCompatActivity
{
    private AppCompatButton saveButton;
    private RadioGroup characterSelection;
    private AppCompatEditText name;
    private DBHandler dbHandler;
    private String[] optionsValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // taskbar eltüntetése
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.options_activity);


        saveButton = (AppCompatButton) findViewById(R.id.save);
        characterSelection = (RadioGroup) findViewById(R.id.characterselection);
        name = (AppCompatEditText) findViewById(R.id.yournametextview);

        dbHandler = new DBHandler(this);
        optionsValue = dbHandler.LoadOption();

        if (optionsValue != null) {
            characterSelection.check(Integer.parseInt(optionsValue[1]));
            name.setText(optionsValue[0]);
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameValue = name.getText().toString().trim();
                String characterValue = String.valueOf(characterSelection.getCheckedRadioButtonId());
                dbHandler.AddOption(nameValue, characterValue);
                finish();
            }
        });
    }



}
