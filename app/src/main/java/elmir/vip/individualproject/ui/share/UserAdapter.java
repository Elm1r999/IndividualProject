package elmir.vip.individualproject.ui.share;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import elmir.vip.individualproject.R;

public class UserAdapter extends FirestoreRecyclerAdapter<UserModel, UserAdapter.UserHolder> {

    Context context;
    private OnItemClickListener listener;

public UserAdapter(@NonNull FirestoreRecyclerOptions<UserModel> options) {
        super(options);
}

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users, parent, false);
        return new UserHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserHolder holder, int position, @NonNull UserModel model) {
        holder.tvUsername.setText(model.getfName());
        holder.tvEmail.setText(model.getEmail());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int postition);
    }

class UserHolder extends RecyclerView.ViewHolder {
    TextView tvUsername;
    TextView tvEmail;

    public UserHolder(View itemView) {
        super(itemView);
        tvUsername = itemView.findViewById(R.id.fName);
        tvEmail = itemView.findViewById(R.id.userEmail);

        itemView.setOnClickListener(view -> {
            int position = getAdapterPosition();
            if (listener != null) {
                listener.onItemClick(getSnapshots().getSnapshot(position), position);
            }
        });
    }
  }
}