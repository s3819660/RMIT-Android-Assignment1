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
import android.widget.Button;

import java.util.Locale;

public class MenuActivity extends AppCompatActivity {
    String name1 = "";
    String name2 = "";
    String themeColor = "";
    String language = "en_US";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set theme according to settings (Approach 1: using SharedPreferences )
        // Get SharedPreferences
        SharedPreferences savedDataSP = getApplicationContext().getSharedPreferences
                                        ("UserPreferences", Context.MODE_PRIVATE);
        // Load saved color theme
        themeColor = savedDataSP.getString("color_theme", "pink");
        setThemeColor(themeColor);
        loadSavedPreferences();
        setLanguage(language);

        // Set theme according to settings (Approach 2: using result code sent from Settings)
//        Intent intent = getIntent();
//        themeColor = intent.getStringExtra("theme_color");
//        setThemeColor(themeColor);

        // Set content view
        setContentView(R.layout.activity_menu);
    }

    public void goToGameActivity(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("name1", name1);
        intent.putExtra("name2", name2);
        startActivityForResult(intent, 200); // From menu directly to game
    }

    public void goToRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, 100); // From menu to register
    }

    public void goToSettingsActivity(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("name1", name1);
        intent.putExtra("name2", name2);
        startActivityForResult(intent, 300); // From menu to settings
    }

    //TODO: rename all goto methods to onBtnClick
    public void goToStart(View view) {
        // Get new names if changes were made in settings
        try {
            // Set name changes from SharedPreferences
            // Get SharedPreferences
            SharedPreferences savedDataSP = getApplicationContext().getSharedPreferences
                    ("UserPreferences", Context.MODE_PRIVATE);
            // Load saved name changes
            if (!savedDataSP.getString("settings_name1", "").isEmpty()) {
                name1 = savedDataSP.getString("settings_name1", "");
                savedDataSP.edit().putString("settings_name1", "").apply();
            }
            if (!savedDataSP.getString("settings_name2", "").isEmpty()) {
                name2 = savedDataSP.getString("settings_name2", "");
                savedDataSP.edit().putString("settings_name2", "").apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (name1.isEmpty()) {
            goToRegisterActivity(view);
        } else {
            goToGameActivity(view);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // If game started by registering name
        // Menu => Register => Game => Menu
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                name1 = intent.getStringExtra("name1");
                name2 = intent.getStringExtra("name2");
            } else {
                System.out.println(resultCode);
                System.out.println("Result from Game CANCELLED");
            }
        }

        // If game started directly
        // Menu => Register => Game => Menu => Game => Menu
        if (requestCode == 200) {

        }

        // Menu => Settings => Menu
        // No longer need this result code since SharedPreferences already handled this
        if (requestCode == 300) {
            if (resultCode == RESULT_OK) {
                // Get theme color data sent from Settings
                themeColor = intent.getStringExtra("theme_color");
                // Approach: finish this Menu and create another Menu to refresh changes
                Intent thisIntent = getIntent();
                thisIntent.putExtra("theme_color", themeColor);
                finish();
                startActivity(getIntent());
                // Approach: recreate() => try this already and it didn't work
                // It was supposed to work????
//                this.recreate();
            } else {
                System.out.println(resultCode);
                System.out.println("Result from settings CANCELLED");
            }
        }
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

//        // Load saved preferences
//        themeColor = savedDataSP.getString("color_theme", "pink");
        // Load saved language from last settings
        language = savedDataSP.getString("language", "en_US");
    }
}