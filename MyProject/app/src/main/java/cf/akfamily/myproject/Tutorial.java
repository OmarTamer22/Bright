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

public class Tutorial extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
    String UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    User user;
    TextView welcomeTxt3;
    Button level0,level1,level2,level3,level4,logoutBtn;
    AlertDialog.Builder alertDialog;
    SharedPreferences sp;
    SharedPreferences.Editor e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        sp = getSharedPreferences("xmlfile",0);
        e = sp.edit();

        welcomeTxt3 = findViewById(R.id.welecomeTxt3);

        ref.child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                welcomeTxt3.setText("Hi "+user.fullName+" !");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(this);

        level0 = findViewById(R.id.level0);
        level1 = findViewById(R.id.level1);
        level2 = findViewById(R.id.level2);
        level3 = findViewById(R.id.level3);
        level4 = findViewById(R.id.level4);
        level0.setOnClickListener(this);
        level1.setOnClickListener(this);
        level2.setOnClickListener(this);
        level3.setOnClickListener(this);
        level4.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.level0:
            case R.id.level1:
            case R.id.level2:
            case R.id.level4:
                Toast.makeText(this, "Not available for now", Toast.LENGTH_SHORT).show();
                break;
            case R.id.level3:
                startActivity(new Intent(Tutorial.this,LevelPage.class));
                break;
            case R.id.logoutBtn:
                alertDialog = new AlertDialog.Builder(Tutorial.this);
                alertDialog.setTitle("Do You Want LogOut ?");
                alertDialog.setCancelable(false);
                alertDialog.setNegativeButton("Stay", new DialogInterface.OnClickListener() {
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
                        startActivity(new Intent(Tutorial.this,MainActivity.class));
                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
                break;
        }

    }
}