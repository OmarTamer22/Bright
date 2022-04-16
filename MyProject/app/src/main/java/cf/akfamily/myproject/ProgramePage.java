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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProgramePage extends AppCompatActivity implements View.OnClickListener {

    //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public String  userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
    public User userOb;
    public TextView welecomeTxt;
    AlertDialog.Builder alertDialog;
    SharedPreferences sp;
    SharedPreferences.Editor e;
    Button logoutBtn , commtree , comptree , regis , schedule , tutorial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programe_page);

        logoutBtn = findViewById(R.id.logoutBtn);
        commtree = findViewById(R.id.commtree);
        comptree = findViewById(R.id.comptree);
        regis = findViewById(R.id.regis);
        schedule = findViewById(R.id.schedule);
        tutorial = findViewById(R.id.tutorial);
        logoutBtn.setOnClickListener(this);
        commtree.setOnClickListener(this);
        comptree.setOnClickListener(this);
        regis.setOnClickListener(this);
        schedule.setOnClickListener(this);
        tutorial.setOnClickListener(this);

        sp = getSharedPreferences("xmlfile",0);
        e = sp.edit();
        String state = sp.getString("account","0");

        welecomeTxt = findViewById(R.id.welecomeTxt2);

        ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userOb = snapshot.getValue(User.class);

                if (userOb != null){
                    welecomeTxt.setText("Hi "+userOb.fullName+" !");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commtree:
                Toast.makeText(ProgramePage.this,"commtree",Toast.LENGTH_SHORT).show();
                break;
            case R.id.comptree:
                break;
            case R.id.regis:
                break;
            case R.id.schedule:
                break;
            case R.id.tutorial:
                break;
            case R.id.logoutBtn:
                alertDialog = new AlertDialog.Builder(ProgramePage.this);
                alertDialog.setTitle("Do You Want LogOut ?");
                alertDialog.setCancelable(false);

                alertDialog.setPositiveButton("LogOut", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        e.putString("account","0");
                        e.commit();
                        startActivity(new Intent(ProgramePage.this,MainActivity.class));
                    }
                });
                alertDialog.setNegativeButton("Stay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
                break;
        }

        }
}