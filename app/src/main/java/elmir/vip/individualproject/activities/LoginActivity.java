package elmir.vip.individualproject.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import elmir.vip.individualproject.R;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mCreateBtn, mForgotPassword;
    FirebaseAuth fAuth;
    AlertDialog dialog;
    TextInputLayout tiEmail, tiPassword;

    @SuppressLint("InflateParams")
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
        tiEmail = findViewById(R.id.emailSignIn);
        tiPassword = findViewById(R.id.passwordSignIn);

        mLoginBtn.setOnClickListener(v -> {

            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if(email.isEmpty()){
                tiEmail.setError("Email is required");
                return;
            }

            if(password.isEmpty()){
                tiPassword.setError("Password is required");
                return;
            }

            if(password.length() < 6){
                tiPassword.setError("Password must have at least 6 characters");
                return;
            }

            // authentication
            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = this.getLayoutInflater();
                    builder.setView(inflater.inflate(R.layout.loading_dialog_alert, null));
                    dialog = builder.create();
                    dialog.show();
                    startActivity(new Intent(getApplicationContext(), NavigationDrawerActivity.class));
                    dialog.dismiss();
                }else {
                    AlertDialog.Builder error_builder = new AlertDialog.Builder(this);
                    error_builder.setTitle("Error, please try again");
                    LinearLayout linearLayout = new LinearLayout(this);
                    TextView textView = new TextView(this);
                    linearLayout.addView(textView);
                    linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                    error_builder.setView(linearLayout);
                    error_builder.setMessage(task.getException().getMessage());
                    error_builder.setNeutralButton("OK", (dialog, which) -> {
                        dialog.dismiss();
                    });
                    error_builder.create().show();
                }
            });
        });

        mCreateBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));

        mForgotPassword.setOnClickListener(v -> recoverPasswordDialog());
    }

    private void recoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover your password");
        LinearLayout linearLayout = new LinearLayout(this);
        EditText emailInput = new EditText(this);
        emailInput.setHint("Email Address");
        emailInput.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailInput.setMinEms(10); //minimum width of email address.
        linearLayout.addView(emailInput);
        builder.setView(linearLayout);
        builder.setPositiveButton("Next", (dialog, which) -> {
            String email = emailInput.getText().toString().trim();
            if(email.isEmpty()){
                emailInput.setHint("Please type correct email address");
            }
            else {
                recoveryFunction(email);
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void recoveryFunction(String email) {
        fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Email sent to "+ email, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}