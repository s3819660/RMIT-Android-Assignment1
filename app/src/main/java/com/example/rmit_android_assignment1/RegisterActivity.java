package com.example.rmit_android_assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    EditText name1EditText;
    EditText name2EditText;
    String name1;
    String name2;
    String themeColor;
    String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load saved preferences from last changes in settings
        loadSavedPreferences();
        setThemeColor(themeColor);
        setLanguage(language);

        setContentView(R.layout.activity_register);
    }

    public void onPlayClick(View view) {
        getViews();

        name1 = name1EditText.getText().toString();
        name2 = name2EditText.getText().toString();

        // Send result back to menu
        // Send data to home so that players can start game again with the same names
        Intent data = new Intent();
        data.putExtra("name1", name1);
        data.putExtra("name2", name2);
        setResult(RESULT_OK, data);

        // Send data forward and start to go to game
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("name1", name1);
        intent.putExtra("name2", name2);
        startActivity(intent);
        finish();
    }

    public void onCancelClick(View view) {
        finish();
    }

    private void getViews() {
        name1EditText = (EditText) findViewById(R.id.player1_name);
        name2EditText = (EditText) findViewById(R.id.player2_name);
    }

    private void setThemeColor(String themeColor) {
        // Try catch in case themeColor is null
        try {
            if (!themeColor.isEmpty()) {
                Window window;
                switch (themeColor) {
                    case "blue":
                        // Set theme from theme.xml
                        // Equivalent to getTheme().applyStyle(R.style.AppTheme_blue, true);
                        setTheme(R.style.AppTheme_blueNoActionBar);
                        // Set status bar color
                        window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getResources().getColor(R.color.blue_900));
                        break;
                    case "green":
                        // Set theme from theme.xml
                        // Equivalent to getTheme().applyStyle(R.style.AppTheme_green, true);
                        setTheme(R.style.AppTheme_greenNoActionBar);
                        // Set status bar color
                        window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getResources().getColor(R.color.green_900));
                        break;
                    default:
                        // Set theme from theme.xml
                        setTheme(R.style.Theme_RMITAndroidAssignment1NoActionBar);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.print("Intial start of app: ");
            System.out.println(e.toString());
        }
    }

    // Set language
    private void setLanguage(String language) {
        Locale locale = new Locale(language);
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    private void loadSavedPreferences() {
        // Get saved SharedPreferences from last changes
        SharedPreferences savedDataSP = getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        // Load saved preferences
        themeColor = savedDataSP.getString("color_theme", "pink");
        // Load saved language from last settings
        language = savedDataSP.getString("language", "en_US");
    }
}