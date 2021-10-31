package com.example.rmit_android_assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    EditText name1EditText;
    EditText name2EditText;
    String name1;
    String name2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void goToGameActivity(View view) {
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

    private void getViews() {
        name1EditText = (EditText) findViewById(R.id.player1_name);
        name2EditText = (EditText) findViewById(R.id.player2_name);
    }
}