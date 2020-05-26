package elmir.vip.individualproject.ui.feed_wall.view_controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import elmir.vip.individualproject.R;
import id.zelory.compressor.Compressor;

public class PostActivity extends AppCompatActivity {

    private Toolbar newPostToolbar;
    private ImageView newPostImage;
    private EditText newPostDesc;
    private Button newPostBtn;
    private Uri postImageuri = null;
    private ProgressBar newPostProgress;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;
    private Bitmap compressedImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        newPostImage = findViewById(R.id.new_post_image);
        newPostDesc = findViewById(R.id.new_post_desc);
        newPostBtn = findViewById(R.id.post_btn);
        newPostProgress = findViewById(R.id.new_post_progress);
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();

        newPostImage.setOnClickListener(v -> CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMinCropResultSize(520, 520)
                .setAspectRatio(1, 1)
                .start(PostActivity.this));

        newPostBtn.setOnClickListener(v -> {

            final String desc = newPostDesc.getText().toString().trim();
            if (!TextUtils.isEmpty(desc) && postImageuri != null) {
                newPostProgress.setVisibility(View.VISIBLE);

                final String randomname = UUID.randomUUID().toString();
                StorageReference filePath = storageReference.child("post_images").child(randomname + ".jpg");
                filePath.putFile(postImageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                        final String downloadUri = task.getResult().getStorage().getDownloadUrl().toString();
                        if (task.isSuccessful()) {
                            File newImageFile = new File(postImageuri.getPath());
                            try {
                                compressedImageFile = new Compressor(PostActivity.this)
                                        .setMaxHeight(100)
                                        .setMaxWidth(100)
                                        .setQuality(3)
                                        .compressToBitmap(newImageFile);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] thumbData = baos.toByteArray();

                            UploadTask uploadTask = storageReference.child("post_images/thumbs").child(randomname + ".jpg").putBytes(thumbData);
                            uploadTask.addOnSuccessListener(taskSnapshot -> {

                                String downloadthumbUri = taskSnapshot.getStorage().getDownloadUrl().toString();

                                Map<String, Object> postmap = new HashMap<>();
                                postmap.put("image_url", downloadUri);
                                postmap.put("image_thumb", downloadthumbUri);
                                postmap.put("desc", desc);
                                postmap.put("user_id", currentUserId);
                                postmap.put("timestamp", FieldValue.serverTimestamp());
                                firebaseFirestore.collection("posts").add(postmap).addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(PostActivity.this, "Post was added", Toast.LENGTH_SHORT).show();
                                        Intent mainIntent = new Intent(PostActivity.this, MainActivity.class);
                                        startActivity(mainIntent);
                                        finish();
                                    }

                                    else {
                                        String error = task1.getException().getMessage();
                                        Toast.makeText(PostActivity.this, "FireStore Upload Error " + error, Toast.LENGTH_SHORT).show();
                                    }
                                    newPostProgress.setVisibility(View.INVISIBLE);
                                });
                            }).addOnFailureListener(e -> {
                                String error = e.getMessage();
                                Toast.makeText(PostActivity.this, "Error " + error, Toast.LENGTH_SHORT).show();
                            });
                        }
                        else {
                            newPostProgress.setVisibility(View.INVISIBLE);
                            String error = task.getException().getMessage();
                            Toast.makeText(PostActivity.this, "Firebase Upload Error " + error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                postImageuri = result.getUri();
                newPostImage.setImageURI(postImageuri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}