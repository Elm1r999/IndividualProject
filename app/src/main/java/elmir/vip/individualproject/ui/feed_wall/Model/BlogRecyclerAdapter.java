package elmir.vip.individualproject.ui.feed_wall.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import elmir.vip.individualproject.R;
import elmir.vip.individualproject.ui.feed_wall.view_controller.CommentsActivity;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder> {

    public List<BlogPost> blog_list;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    public Context context;
    public BlogRecyclerAdapter(List<BlogPost> blog_list){
        this.blog_list= blog_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.blog_list_item,viewGroup,false);
         context = viewGroup.getContext();
         firebaseFirestore = FirebaseFirestore.getInstance();
         firebaseAuth = FirebaseAuth.getInstance();
         return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        viewHolder.setIsRecyclable(false);

        final String blogPostId = blog_list.get(i).BlogPostId;
        final String currentUserId = firebaseAuth.getCurrentUser().getUid();

        String desc_data = blog_list.get(i).getDesc();
        viewHolder.setDescText(desc_data);
        String image_uri = blog_list.get(i).getImage_url();
        String thumbUri = blog_list.get(i).getImage_thumb();
        viewHolder.setBlogImage(image_uri,thumbUri);

        String user_id = blog_list.get(i).getUser_id();
        firebaseFirestore.collection("users").document(user_id).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String userName = task.getResult().getString("fName");
                String userImage = task.getResult().getString("image");
                viewHolder.setData(userName, userImage);
            }
            else {
                String error = task.getException().getMessage();
                Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        //convert timestamp into date
        long millisecond = blog_list.get(i).getTimestamp().getTime();
        @SuppressLint("SimpleDateFormat")
        String dateString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(millisecond));
        viewHolder.setDate(dateString);

        firebaseFirestore.collection("posts/" + blogPostId + "/likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (documentSnapshots != null) {
                    if (!documentSnapshots.isEmpty()) {
                        viewHolder.updateLikesCount(documentSnapshots.size() + "");
                    } else {
                        viewHolder.updateLikesCount("0");
                    }
                }
            }
        });

        firebaseFirestore.collection("posts/" + blogPostId + "/comments").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (documentSnapshots != null) {
                    if (!documentSnapshots.isEmpty()) {
                        viewHolder.updateCommentsCount(documentSnapshots.size() + "");
                    } else {
                        viewHolder.updateCommentsCount("0");
                    }
                }
            }
        });

        firebaseFirestore.collection("posts/"+blogPostId+"/likes").document(currentUserId)
                .addSnapshotListener((documentSnapshot, e) -> {
            if (documentSnapshot!= null) {
                if (documentSnapshot.exists()) {
                    viewHolder.blogLikeBtn.setImageResource(R.drawable.like_accent);
                } else {
                    viewHolder.blogLikeBtn.setImageResource(R.drawable.like_grey);
                }
            }
        });

        viewHolder.blogLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseFirestore.collection("posts/"+blogPostId+"/likes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(!task.getResult().exists()){
                            Map<String, Object> likesMap = new HashMap<>();
                            likesMap.put("timestamp", FieldValue.serverTimestamp());

                            firebaseFirestore.collection("posts/"+blogPostId+"/likes").document(currentUserId).set(likesMap);
                        }
                        else {
                            firebaseFirestore.collection("posts/"+blogPostId+"/likes").document(currentUserId).delete();

                        }
                    }
                });


            }
        });

        viewHolder.blogCommentBtn.setOnClickListener(v -> {
            Intent commentIntent = new Intent(context, CommentsActivity.class);
            commentIntent.putExtra("blog_post_id", blogPostId);
            context.startActivity(commentIntent);
        });
    }

    @Override
    public int getItemCount() {
        return blog_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public View mView;
        public TextView descView;
        public ImageView blogImageView;
        public TextView blogDate;
        public TextView blogUserName;
        public CircleImageView blogUserImage;
        public ImageView blogLikeBtn;
        public TextView blogLikeCount, blogCommentCount;
        private ImageView blogCommentBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            blogLikeBtn = mView.findViewById(R.id.blog_like_btn);
            blogLikeCount = mView.findViewById(R.id.blog_like_counter);
            blogCommentBtn = mView.findViewById(R.id.blog_comment_icon);
        }

        public void setDescText(String descText){
            descView= mView.findViewById(R.id.blog_desc);
            descView.setText(descText);
        }

        public void setBlogImage(String downloadUri, String thumbUri) {

            blogImageView = mView.findViewById(R.id.blog_image);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_person_black_24dp);

            Glide.with(context).applyDefaultRequestOptions(requestOptions).load(downloadUri).thumbnail(
                    Glide.with(context).load(thumbUri)
            ).into(blogImageView);
        }

        public void setDate(String date){
            blogDate = mView.findViewById(R.id.blog_date);
            blogDate.setText(date);
        }

        public  void setData(String name, String image){
            blogUserName = mView.findViewById(R.id.blog_user_name);
            blogUserImage = mView.findViewById(R.id.blog_user_image);
            blogUserName.setText(name);

            RequestOptions placeholderOption =new RequestOptions();
            placeholderOption.placeholder(R.drawable.ic_person_black_24dp);
            Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(image).into(blogUserImage);
        }

        public void updateLikesCount(String count) {
            blogLikeCount = mView.findViewById(R.id.blog_like_counter);
            blogLikeCount.setText(count + " Likes");
        }

        public void updateCommentsCount(String count) {
            blogCommentCount = mView.findViewById(R.id.blog_comment_count);
            blogCommentCount.setText(count + " Comments");
        }
    }
}