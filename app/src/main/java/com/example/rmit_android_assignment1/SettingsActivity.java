package com.example.rmit_android_assignment1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {
    private String themeColor = "";
    private String prevName1 = "";
    private String prevName2 = "";
    private String changeName1 = "";
    private String changeName2 = "";
    private boolean isNightModeLocal = true;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load changes made last time
        loadSavedPreferences();
        // Set theme color as changed last time
        setThemeColor(themeColor); // case already changed in settings then exit and start app again

        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Check if default local theme is dark mode
        isNightModeLocal = isDarkModeLocal();
        // Load default dark mode of local to app
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean("dark_mode", isNightModeLocal).apply();
        // Empty Edit names
        sharedPreferences.edit().putString("edit_player1_name", "").apply();
        sharedPreferences.edit().putString("edit_player2_name", "").apply();
        // Redraw/Update View
        invalidateOptionsMenu();

        // Get saved names from Menu if available
        Intent intent = getIntent();
        prevName1 = intent.getStringExtra("name1");
        prevName2 = intent.getStringExtra("name2");
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Respond to the action bar's Up/Home button
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // When the Save & Exit button is clicked event
    public void onSaveBtnClick(View view) {
        loadSettings();

        // Set result and finish to go back to Menu
        // Also send theme_color to set theme in Menu
        Intent data = new Intent();

        // Put extras to send back to menu
        // Theme color
        data.putExtra("theme_color", themeColor);
//        // Names if changes were made
//        if (!changeName1.isEmpty()) {
//            changeName2 = prevName2;
//        } else if (!changeName2.isEmpty()) {
//            changeName1 = prevName1;
//        }
//        data.putExtra("settings_name1", changeName1);
//        data.putExtra("settings_name2", changeName2);

        // Set result and finish activity
        setResult(RESULT_OK, data);
        finish();
    }

    // Check if any changes was made
    // And save the settings by using SharedPreferences
    private void loadSettings() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Get theme color change in Settings
        themeColor = sharedPreferences.getString("color_theme", "pink");

        // Check the dark mode enable switch
        boolean checkNightModeToggle = sharedPreferences.getBoolean("dark_mode", false);
        if (checkNightModeToggle != isNightModeLocal) {
            if (checkNightModeToggle)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Get players' names change in settings
        changeName1 = sharedPreferences.getString("edit_player1_name", "");
        changeName2 = sharedPreferences.getString("edit_player2_name", "");
        if (changeName1.isEmpty())
            changeName1 = prevName1;
        if (changeName2.isEmpty())
            changeName2 = prevName2;

        // Save to SharedPreferences
        // Create "UserPreferences" SharedPreferences to store settings data
        SharedPreferences dataSP = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        // Create editor
        SharedPreferences.Editor editor = dataSP.edit();
        // Put data in editor
        editor.putString("color_theme", themeColor);
        editor.putBoolean("dark_mode", checkNightModeToggle);
        editor.putString("settings_name1", changeName1);
        editor.putString("settings_name2", changeName2);
        // Apply changes made to preferences from this editor
        editor.apply();
    }

    // Check if dark mode in local
    private boolean isDarkModeLocal() {
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                isNightModeLocal = true;
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                isNightModeLocal = false;
                break;
        }

        return isNightModeLocal;
    }

    // Set theme color
    private void setThemeColor(String themeColor) {
        // Try catch in case themeColor is null
        try {
            if (!themeColor.isEmpty()) {
                Window window;
                switch (themeColor) {
                    case "blue":
                        // Set theme from theme.xml
                        // Equivalent to getTheme().applyStyle(R.style.AppTheme_blue, true);
                        setTheme(R.style.AppTheme_blue);
                        // Set status bar color
                        window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getResources().getColor(R.color.blue_900));
                        break;
                    case "green":
                        // Set theme from theme.xml
                        // Equivalent to getTheme().applyStyle(R.style.AppTheme_green, true);
                        setTheme(R.style.AppTheme_green);
                        // Set status bar color
                        window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getResources().getColor(R.color.green_900));
                        break;
                    default:
                        // Set theme from theme.xml
                        setTheme(R.style.Theme_RMITAndroidAssignment1);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void loadSavedPreferences() {
        // Get saved SharedPreferences from last changes
        SharedPreferences savedDataSP = getApplicationContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        // Load saved preferences
        themeColor = savedDataSP.getString("color_theme", "pink");

    }

    // Save settings to a file
//    private void saveSettings() {
        // Store settings data to a JSON object
//        JSONObject settingsDataJSON = new JSONObject();
//        try {
//            settingsDataJSON.put("dark_mode", checkNightMode);
//            settingsDataJSON.put("color_theme", themeColor);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        // Save JSON object to JSON file
//        fileWriter = new FileWriter();
//    }
}