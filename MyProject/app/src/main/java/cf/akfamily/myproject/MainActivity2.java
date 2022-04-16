package cf.akfamily.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toast.makeText(MainActivity2.this,"Successful Log In",Toast.LENGTH_LONG).show();

    }

    public void colse(View view) {
        Toast.makeText(MainActivity2.this,"closed",Toast.LENGTH_SHORT).show();
    }
}