package cf.akfamily.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegisterUser extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText fullName,age,email,password,phoneNumber;
    Button registerbtn;
    ProgressBar progressBar;
    Spinner mySpinner;
    TextInputLayout programe;
    String Programe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();
        fullName= findViewById(R.id.fullName);
        age= findViewById(R.id.age);
        email= findViewById(R.id.email);
        password= findViewById(R.id.password);
        phoneNumber= findViewById(R.id.phoneNumber);
        registerbtn= findViewById(R.id.registerBtn);
        progressBar=findViewById(R.id.progressBar);
        programe=findViewById(R.id.programe);
        mySpinner = findViewById(R.id.spinner);

        ArrayList<String> programes = new ArrayList<>();
        programes.add("Choose Your Programe");
        programes.add("CCE");
        programes.add("IE");
        programes.add("EPE");
        programes.add("MPE");
        programes.add("CE");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, programes);

        mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Programe = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });


    }

    private void registerUser() {
        String Fullname = fullName.getText().toString().trim();
        String Age = age.getText().toString().trim();
        String Mail = email.getText().toString().trim();
        String Pass = password.getText().toString().trim();
        String Phone = phoneNumber.getText().toString().trim();

        if(Fullname.isEmpty()){
            fullName.setError("Full Name is required");
            fullName.requestFocus();
            return;
        }
        if(Age.isEmpty()){
            age.setError("Age is required");
            age.requestFocus();
            return;
        }
        if(Mail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Mail.contains("@feng.bu.edu.eg")){
            email.setError("please enter your collage email! '@feng.bu.edu'");
            email.requestFocus();
            return;
        }

        if(Pass.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if(Pass.length()<6){
            password.setError("Minimum Length is 6 character!");
            password.requestFocus();
            return;
        }
        if(Phone.isEmpty()){
            phoneNumber.setError("Phone Number is required");
            phoneNumber.requestFocus();
            return;
        }
        if(Phone.length()!=11){
            phoneNumber.setError("Phone Number is wrong check again");
            phoneNumber.requestFocus();
            return;
        }
        if(Programe.equals("Choose Your Programe")){
            programe.setError("Choose Your Programe");
            programe.requestFocus();
            return;
        }



        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(Mail,Pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(Fullname,Age,Mail,Pass,Phone,Programe);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this,"Successfully Register",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);

                                        Intent intent = new Intent(RegisterUser.this,MainActivity.class);
                                        startActivity(intent);

                                    }
                                    else{
                                        Toast.makeText(RegisterUser.this,"Failed to Register! Try Again",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(RegisterUser.this,"Failed to Register! ",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


    }
}