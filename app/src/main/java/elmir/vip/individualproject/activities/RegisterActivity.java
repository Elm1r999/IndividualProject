package elmir.vip.individualproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import elmir.vip.individualproject.R;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mFullName,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    TextInputLayout textInputPhoneNo, textInputPassword;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName   = findViewById(R.id.fullName);
        mEmail      = findViewById(R.id.Email);
        mPassword   = findViewById(R.id.password);
        mPhone      = findViewById(R.id.phone);
        mRegisterBtn= findViewById(R.id.registerBtn);
        mLoginBtn   = findViewById(R.id.create_newAcc_link);
        textInputPhoneNo = findViewById(R.id.phoneTextInputLayout);
        textInputPassword = findViewById(R.id.passwordTextInputLayout);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        mRegisterBtn.setOnClickListener(v -> {
            final String fullName = mFullName.getText().toString();
            String phone = mPhone.getText().toString().trim();
            final String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                mEmail.setError("Field cannot be empty");
                return;
            }

            if(TextUtils.isEmpty(password)){
                textInputPassword.setError("Field cannot be empty");
                return;
            }

            if(phone.length() > 15){
                textInputPhoneNo.setError("Maximum 15 digits");
                return;
            }

            if(password.length() < 6){
                textInputPassword.setError("Password must be minimum 6 characters");
                return;
            }

            // register the user in firebase
            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = this.getLayoutInflater();
                    builder.setView(inflater.inflate(R.layout.loading_dialog_alert, null));
                    dialog = builder.create();
                    dialog.show();

                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users").document(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("fName",fullName);
                    user.put("email",email);
                    user.put("phone",phone);
                    documentReference.set(user).addOnSuccessListener(aVoid -> Log.d(TAG, "onSuccess: User profile is created for "+ userID))
                                               .addOnFailureListener(e -> Log.d(TAG, "onFailure: " + e.toString()));
                    startActivity(new Intent(getApplicationContext(), NavigationDrawerActivity.class));
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = this.getLayoutInflater();
                    builder.setView(inflater.inflate(R.layout.loading_dialog_alert, null));
                    dialog = builder.create();
                    dialog.show();

                    AlertDialog.Builder error_builder = new AlertDialog.Builder(this);
                    error_builder.setTitle("Error, please try again");
                    LinearLayout linearLayout = new LinearLayout(this);
                    TextView textView = new TextView(this);
                    linearLayout.addView(textView);
                    error_builder.setView(linearLayout);
                    error_builder.setMessage(task.getException().getMessage());
                    error_builder.setNeutralButton("OK", (dialog, which) -> {
                        dialog.dismiss();
                    });
                    error_builder.create().show();
                }
            });
        });
        mLoginBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));
    }
}