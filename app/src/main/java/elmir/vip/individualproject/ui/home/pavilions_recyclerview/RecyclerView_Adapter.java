package elmir.vip.individualproject.ui.home.pavilions_recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import elmir.vip.individualproject.R;

public class RecyclerView_Adapter extends RecyclerView.Adapter<ViewHolder> {

    Context c;
    ArrayList<Pavilion> pavilions;

    public RecyclerView_Adapter(Context c, ArrayList<Pavilion> pavilions) {
        this.c = c;
        this.pavilions = pavilions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_list, parent,false);
        ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTxt.setText(pavilions.get(position).getName()); //Bind Data
        PicassoClient.downloadImage(c, pavilions.get(position).getUrl(), holder.img); //Image
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(c, NewActivity.class);
//
//                // passing data to the book activity
//                intent.putExtra("Title", "");
//                intent.putExtra("Description", "");
//                start the activity
//                c.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return pavilions.size();
    }
}