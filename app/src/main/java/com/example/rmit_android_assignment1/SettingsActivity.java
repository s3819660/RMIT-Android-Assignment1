package com.example.rmit_android_assignment1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {
    String themeColor = "";
    boolean isNightMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        isNightMode = false;
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                isNightMode = true;
//                System.out.println("night mode");
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                isNightMode = false;
//                System.out.println("day mode");
                break;
        }

        // Load default dark mode
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean("dark_mode", isNightMode).apply();
        boolean nightModeCheck = sharedPreferences.getBoolean("dark_mode", false);
//        System.out.println("nighhtmodecheck=" + nightModeCheck);

        //TODO: menu => settings => menu => settings (does not keep the same theme color)

        // Redraw/Update View
        invalidateOptionsMenu();
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

    public void onSaveBtnClick(View view) {
        loadSettings();
        Intent data = new Intent();
        data.putExtra("theme_color", themeColor);
        setResult(RESULT_OK, data);
        finish();
    }

    private void loadSettings() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        themeColor = sharedPreferences.getString("color_theme", "pink");
//        System.out.println("colortheme=" + themeColor);

        boolean checkNightMode = sharedPreferences.getBoolean("dark_mode", false);
        if (checkNightMode != isNightMode) {
            if (checkNightMode)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}