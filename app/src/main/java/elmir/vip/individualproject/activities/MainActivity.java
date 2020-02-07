package elmir.vip.individualproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import elmir.vip.individualproject.R;

public class MainActivity extends AppCompatActivity {
    Button loginBtn, registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.btn_login);
        registerBtn = findViewById(R.id.btn_register);


        registerBtn.setOnClickListener(view -> {
            Intent registerActivity = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(registerActivity);
        });

        loginBtn.setOnClickListener(view -> {
            Intent loginActivity = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginActivity);
        });
    }
}