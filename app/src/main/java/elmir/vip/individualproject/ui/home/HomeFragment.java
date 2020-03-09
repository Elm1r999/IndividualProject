package elmir.vip.individualproject.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import elmir.vip.individualproject.R;
import elmir.vip.individualproject.ui.home.about_expo.AboutExpoActivity;
import elmir.vip.individualproject.ui.home.events.EventsActivity;
import elmir.vip.individualproject.ui.home.pavilions.PavilionActivity;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView imgTickets = root.findViewById(R.id.imgTickets);
        imgTickets.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), BuyTicketsActivity.class);
            startActivity(intent);
        });

        ImageView imgScanner = root.findViewById(R.id.imgScanner);
        imgScanner.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), BarcodeGeneratorActivity.class);
            startActivity(intent);
        });

        ImageView imgSchedule = root.findViewById(R.id.imgSchedule);
        imgSchedule.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EventsActivity.class);
            startActivity(intent);
        });

        ImageView imgPavilions = root.findViewById(R.id.imgPavilions);
        imgPavilions.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PavilionActivity.class);
            startActivity(intent);
        });

        ImageView img_AboutExpo = root.findViewById(R.id.img_AboutExpo);
        img_AboutExpo.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AboutExpoActivity.class);
            startActivity(intent);
        });

        ImageView img_AboutUae = root.findViewById(R.id.img_AboutUae);
        img_AboutUae.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AboutUAEActivity.class);
            startActivity(intent);
        });
        return root;
    }
}