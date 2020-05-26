package elmir.vip.individualproject.ui.home.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import elmir.vip.individualproject.R;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{
    private Context context;
    private List<Event> eventList;

    public EventAdapter(Context context, List<Event> eventList){
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_event, null);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {
        Event event = eventList.get(i);
        eventViewHolder.eventTitle.setText(event.getEvenTitle());
        eventViewHolder.eventCategory.setText(event.getEventCategory());
        eventViewHolder.eventPicture.setImageDrawable(context.getResources().getDrawable(event.getEventPicture()));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle, eventCategory;
        ImageView eventPicture;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.eventtitle);
            eventCategory = itemView.findViewById(R.id.eventcategory);
            eventPicture = itemView.findViewById(R.id.eventpicture);
        }
    }
}

