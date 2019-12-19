package elmir.vip.individualproject.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import elmir.vip.individualproject.R;

public class BuyTicketsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_tickets);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
