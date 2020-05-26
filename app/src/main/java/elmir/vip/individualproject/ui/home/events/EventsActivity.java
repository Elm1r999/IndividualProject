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

        eventList.add(new Event("Live Performances","ENTERTAINMENT", R.drawable.expo2020_liveentertainment));
        eventList.add(new Event("Cultural Experiences", "ART", R.drawable.expo2020_arts_culture));
        eventList.add(new Event("Business and Innovations", "SEMINAR", R.drawable.expo2020_business));
        eventList.add(new Event("National Days", "FESTIVAL", R.drawable.expo2020_nationaldays));
        eventList.add(new Event("Global Cuisines", "FESTIVAL", R.drawable.expo2020_food));
        eventList.add(new Event("Nightlife Shows", "ENTERTAINMENT", R.drawable.expo2020_nightshow));

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        eventsPlace.setLayoutManager(linearLayoutManager);
        eventsPlace.setHasFixedSize(true);

        eventAdapter = new EventAdapter(this, eventList);
        eventsPlace.setAdapter(eventAdapter);

        // snapping the scroll items
        final SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
        snapHelper.attachToRecyclerView(eventsPlace);

        // set a timer for default item
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            RecyclerView.ViewHolder viewHolderDefault = eventsPlace.findViewHolderForAdapterPosition(0);

            LinearLayout eventparentDefault = viewHolderDefault.itemView.findViewById(R.id.eventparent);
            eventparentDefault.animate().scaleY(1).scaleX(1).setDuration(350).setInterpolator(new AccelerateInterpolator()).start();

            LinearLayout eventcategoryDefault = viewHolderDefault.itemView.findViewById(R.id.eventbadge);
            eventcategoryDefault.animate().alpha(1).setDuration(300).start();
        }, 100);

        // add animate scroll
        eventsPlace.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    View view = snapHelper.findSnapView(linearLayoutManager);
                    int pos = linearLayoutManager.getPosition(view);

                    RecyclerView.ViewHolder viewHolder = eventsPlace.findViewHolderForAdapterPosition(pos);

                    LinearLayout eventParent = viewHolder.itemView.findViewById(R.id.eventparent);
                    eventParent.animate().scaleY(1).scaleX(1).setDuration(350).setInterpolator(new AccelerateInterpolator()).start();

                    LinearLayout eventCategory = viewHolder.itemView.findViewById(R.id.eventbadge);
                    eventCategory.animate().alpha(1).setDuration(300).start();

                }
                else {

                    View view = snapHelper.findSnapView(linearLayoutManager);
                    int pos = linearLayoutManager.getPosition(view);

                    RecyclerView.ViewHolder viewHolder = eventsPlace.findViewHolderForAdapterPosition(pos);

                    LinearLayout eventParent = viewHolder.itemView.findViewById(R.id.eventparent);
                    eventParent.animate().scaleY(0.7f).scaleX(0.7f).setInterpolator(new AccelerateInterpolator()).setDuration(350).start();

                    LinearLayout eventCategory = viewHolder.itemView.findViewById(R.id.eventbadge);
                    eventCategory.animate().alpha(0).setDuration(300).start();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
}