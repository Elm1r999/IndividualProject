package elmir.vip.individualproject.ui.home.events;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.ArrayList;
import java.util.List;

import elmir.vip.individualproject.R;

public class EventsActivity extends AppCompatActivity {

    RecyclerView eventsPlace;
    EventAdapter eventAdapter;
    List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        eventsPlace = findViewById(R.id.eventsplace);

        eventList = new ArrayList<>();
        eventList.add(
                new Event(
                        "Ecoturism with a Master Pank",
                        "WORKSHOP",
                        R.drawable.picone
                )
        );

        eventList.add(
                new Event(
                        "Cooking with Me and Julie",
                        "CLASSES",
                        R.drawable.pictwo
                )
        );

        eventList.add(
                new Event(
                        "Learn Gratitude from Monk",
                        "SEMINAR",
                        R.drawable.picone
                )
        );
        eventList.add(
                new Event(
                        "Ecoturism with a Master Pank",
                        "WORKSHOP",
                        R.drawable.picone
                )
        );

        eventList.add(
                new Event(
                        "Cooking with Me and Julie",
                        "CLASSES",
                        R.drawable.pictwo
                )
        );

        eventList.add(
                new Event(
                        "Learn Gratitude from Monk",
                        "SEMINAR",
                        R.drawable.picone
                )
        );

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager
                (this, LinearLayoutManager.HORIZONTAL, false);

        eventsPlace.setLayoutManager(linearLayoutManager);
        eventsPlace.setHasFixedSize(true);

        eventAdapter = new EventAdapter(this, eventList);
        eventsPlace.setAdapter(eventAdapter);

        // snapping the scroll items
        final SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
        snapHelper.attachToRecyclerView(eventsPlace);



        // set a timer for default item
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 1ms = 100ms
                RecyclerView.ViewHolder viewHolderDefault = eventsPlace.
                        findViewHolderForAdapterPosition(0);

                LinearLayout eventparentDefault = viewHolderDefault.itemView.
                        findViewById(R.id.eventparent);
                eventparentDefault.animate().scaleY(1).scaleX(1).setDuration(350).
                        setInterpolator(new AccelerateInterpolator()).start();

                LinearLayout eventcategoryDefault = viewHolderDefault.itemView.
                        findViewById(R.id.eventbadge);
                eventcategoryDefault.animate().alpha(1).setDuration(300).start();

            }
        }, 100);



        // add animate scroll
        eventsPlace.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    View view = snapHelper.findSnapView(linearLayoutManager);
                    int pos = linearLayoutManager.getPosition(view);

                    RecyclerView.ViewHolder viewHolder =
                            eventsPlace.findViewHolderForAdapterPosition(pos);

                    LinearLayout eventparent = viewHolder.itemView.findViewById(R.id.eventparent);
                    eventparent.animate().scaleY(1).scaleX(1).setDuration(350).
                            setInterpolator(new AccelerateInterpolator()).start();

                    LinearLayout eventcategory = viewHolder.itemView.
                            findViewById(R.id.eventbadge);
                    eventcategory.animate().alpha(1).setDuration(300).start();

                }
                else {

                    View view = snapHelper.findSnapView(linearLayoutManager);
                    int pos = linearLayoutManager.getPosition(view);

                    RecyclerView.ViewHolder viewHolder =
                            eventsPlace.findViewHolderForAdapterPosition(pos);

                    LinearLayout eventparent = viewHolder.itemView.findViewById(R.id.eventparent);
                    eventparent.animate().scaleY(0.7f).scaleX(0.7f).
                            setInterpolator(new AccelerateInterpolator()).setDuration(350).start();

                    LinearLayout eventcategory = viewHolder.itemView.
                            findViewById(R.id.eventbadge);
                    eventcategory.animate().alpha(0).setDuration(300).start();
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }
}
