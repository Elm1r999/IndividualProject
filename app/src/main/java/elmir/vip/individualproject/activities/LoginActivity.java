package elmir.vip.individualproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import elmir.vip.individualproject.R;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mCreateBtn, mForgotPassword;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.loginBtn);
        mCreateBtn = findViewById(R.id.create_newAcc_link);
        mForgotPassword = findViewById(R.id.forgot_password);

        mLoginBtn.setOnClickListener(v -> {

            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                mEmail.setError("Email is required.");
                return;
            }

            if(TextUtils.isEmpty(password)){
                mPassword.setError("Password is required.");
                return;
            }

            if(password.length() < 6){
                mPassword.setError("Password must have at least 6 characters");
                return;
            }

            // authenticate the user
            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), NavigationDrawerActivity.class));
                }else {
                    Toast.makeText(LoginActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        mCreateBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));

        mForgotPassword.setOnClickListener(v -> recoverPasswordDialog());
    }

    private void recoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Password Recovery");
        LinearLayout linearLayout = new LinearLayout(this);
        EditText emailInput = new EditText(this);
        emailInput.setHint("Email Address");
        emailInput.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailInput.setMinEms(15);
        linearLayout.addView(emailInput);

        builder.setView(linearLayout);

        builder.setPositiveButton("Next", (dialog, which) -> {
            String email = emailInput.getText().toString().trim();
            recoveryFunction(email);

        });

        builder.setNegativeButton("Cancel ", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void recoveryFunction(String email) {
        fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}