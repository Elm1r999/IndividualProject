package elmir.vip.individualproject.ui.home.pavilions_recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import elmir.vip.individualproject.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView nameTxt;
    ImageView img;
    CardView cardView;

    public ViewHolder(View itemView) {
        super(itemView);
        nameTxt = itemView.findViewById(R.id.pavilion_title);
        img = itemView.findViewById(R.id.pavilion_imgID);
        cardView = itemView.findViewById(R.id.cardview_id);
    }
}