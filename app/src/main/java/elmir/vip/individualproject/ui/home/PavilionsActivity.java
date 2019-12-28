package elmir.vip.individualproject.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import elmir.vip.individualproject.R;
import elmir.vip.individualproject.ui.home.pavilions_recyclerview.PavilionsCollection;
import elmir.vip.individualproject.ui.home.pavilions_recyclerview.RecyclerView_Adapter;

public class PavilionsActivity extends AppCompatActivity {

    RecyclerView rv;
    RecyclerView_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pavilions);

        rv =  findViewById(R.id.pavilionsRecyclerView);
        rv.setLayoutManager(new GridLayoutManager(this, 3));

        adapter = new RecyclerView_Adapter(this, PavilionsCollection.getPavilions());
        rv.setAdapter(adapter);
    }
}