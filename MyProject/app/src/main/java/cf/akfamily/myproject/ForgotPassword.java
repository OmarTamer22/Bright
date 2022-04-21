package cf.akfamily.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button resetBtn;
    TextInputEditText emailReset;
    ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth= FirebaseAuth.getInstance();
        emailReset=findViewById(R.id.emailReset);
        progressBar2=findViewById(R.id.progressBar2);
        resetBtn = findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetPass();

            }
        });


    }

    private void resetPass() {
        String EmailReset = emailReset.getText().toString().trim();
        if(EmailReset.isEmpty()){
            emailReset.setError("Email is required");
            emailReset.requestFocus();
            return;        }

        progressBar2.setVisibility(View.VISIBLE);

        mAuth.sendPasswordResetEmail(EmailReset).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"Check your email to reset your Password",Toast.LENGTH_LONG).show();
                    progressBar2.setVisibility(View.GONE);

                }
                else {
                    Toast.makeText(ForgotPassword.this,"Invalid email",Toast.LENGTH_LONG).show();
                    progressBar2.setVisibility(View.GONE);
                }
            }
        });
    }
}