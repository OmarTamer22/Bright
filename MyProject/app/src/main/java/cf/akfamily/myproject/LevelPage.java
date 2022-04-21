package cf.akfamily.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LevelPage extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
    String UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    User user;
    SharedPreferences sp;
    SharedPreferences.Editor e;
    AlertDialog.Builder alertDialog;
    TextView welcomeTxt;
    Button course1 , course2 , course3 , course4 , course5 , course6 , logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_page);

        sp = getSharedPreferences("xmlfile",0);
        e = sp.edit();
        alertDialog = new AlertDialog.Builder(LevelPage.this);
        welcomeTxt = findViewById(R.id.welecomeTxt);

        ref.child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                welcomeTxt.setText("Hi "+ user.fullName+" !");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(this);
        course1 = findViewById(R.id.course1);
        course2 = findViewById(R.id.course2);
        course3 = findViewById(R.id.course3);
        course4 = findViewById(R.id.course4);
        course5 = findViewById(R.id.course5);
        course6 = findViewById(R.id.course6);
        course1.setOnClickListener(this);
        course2.setOnClickListener(this);
        course3.setOnClickListener(this);
        course4.setOnClickListener(this);
        course5.setOnClickListener(this);
        course6.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.course1:
                    break;
            case R.id.course2:
            case R.id.course3:
            case R.id.course4:
            case R.id.course5:
            case R.id.course6:
                Toast.makeText(this, "Not available for now", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logoutBtn:
                alertDialog.setTitle("Do You Want LogOut ?");
                alertDialog.setCancelable(false);
                alertDialog.setNegativeButton("stay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        e.putString("account","0");
                        e.commit();
                        startActivity(new Intent(LevelPage.this,MainActivity.class));
                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
                break;
        }

    }
}