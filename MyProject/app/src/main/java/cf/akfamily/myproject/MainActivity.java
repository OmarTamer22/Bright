package cf.akfamily.myproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText email ;
    EditText pass ;
    TextView err,register,forgot ;
    Button loginBtn;
    ProgressBar progressBar;
    SharedPreferences sp;
    SharedPreferences.Editor e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.EmailLog);
        pass = findViewById(R.id.PasswordLog);
        err = findViewById(R.id.err);
        register = findViewById(R.id.register);
        forgot = findViewById(R.id.forgot);
        progressBar= findViewById(R.id.progressBar);
        loginBtn = findViewById(R.id.LoginBtn);
        sp = getSharedPreferences("xmlfile",0);
        e = sp.edit();
        String state = sp.getString("account","0");


        if(state.equals("1")){
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(sp.getString("Email",""),sp.getString("Password","")).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(MainActivity.this,HomePage.class);
                        startActivity(intent);
                        finish();
                    }

                }
            });

        }


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userlogin();
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(MainActivity.this,ForgotPassword.class);
                startActivity(inten);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterUser.class);
                startActivity(intent);
            }
        });




    }

    private void userlogin() {
        String Email = email.getText().toString().trim();
        String Password = pass.getText().toString().trim();

        if(Email.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Email.contains("@feng.bu.edu.eg")){
            email.setError("please enter your collage email '@feng.bu.edu' ");
            email.requestFocus();
            return;
        }
        if (Password.isEmpty()){
            pass.setError("Enter Password");
            pass.requestFocus();
            return;
        }
        if(Password.length()<6){
            pass.setError("Minimum Length is 6 character!");
            pass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(MainActivity.this,HomePage.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this,"Successfully LogIn",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            e.putString("account","1");
                            e.putString("Email",Email);
                            e.putString("Password",Password);
                            e.commit();
                        }
                        else{
                           err.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this,"Incorrect Account",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

    }



}