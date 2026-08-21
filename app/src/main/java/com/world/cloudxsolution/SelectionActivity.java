package com.world.cloudxsolution;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        Button btnRomstation = findViewById(R.id.btn_romstation);
        Button btnXbox = findViewById(R.id.btn_xbox);
        Button btnPlaystation = findViewById(R.id.btn_playstation);

        btnRomstation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectionActivity.this, com.world.cloudxsolution.romstation.MainActivity.class);
                startActivity(intent);
            }
        });

        btnXbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectionActivity.this, com.world.cloudxsolution.xbox.MainActivity.class);
                startActivity(intent);
            }
        });

        btnPlaystation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectionActivity.this, com.world.cloudxsolution.playstation.MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
