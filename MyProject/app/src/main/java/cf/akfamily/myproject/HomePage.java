package cf.akfamily.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity {

    TextView textWelcome;
    public FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
    public String userID = user.getUid();
    public User userOb;
    Button logoutBtn;
    SharedPreferences sp;
    SharedPreferences.Editor e;
    CardView cardViewAd;
    AlertDialog.Builder alertDialog;
    Button cceBtn;
    public String Programe;
    ProgressBar progressBar;
    boolean ready ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        textWelcome = findViewById(R.id.textWelcome);

        sp = getSharedPreferences("xmlfile",0);
        e = sp.edit();
        String state = sp.getString("account","0");


        reference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userOb = snapshot.getValue(User.class);

                if(userOb != null){
                    String Name=userOb.fullName;
                    Programe=userOb.programe;
                    textWelcome.setText("Hi "+userOb.fullName+" !");
                    progressBar.setVisibility(View.GONE);
                    ready = true;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        cceBtn = findViewById(R.id.cceBtn);
        cceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ready) {
                    if (userOb.programe.equals("CCE")) {
                        Intent intent = new Intent(HomePage.this, ProgramePage.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(HomePage.this, "You don't Belong to This Programe", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = new AlertDialog.Builder(HomePage.this);
                alertDialog.setTitle("Do You Want LogOut ?");
                alertDialog.setCancelable(false);

                alertDialog.setPositiveButton("LogOut", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        e.putString("account","0");
                        e.commit();
                        startActivity(new Intent(HomePage.this,MainActivity.class));
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
            }
        });

    }

}