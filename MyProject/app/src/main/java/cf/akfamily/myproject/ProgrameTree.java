package cf.akfamily.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class ProgrameTree extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programe_tree);

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setInitialScale(1);

        String Programe = getIntent().getExtras().getString("Programe");
        if (Programe.equals("comm")) {
            webView.loadUrl("file:///android_asset/commtree.jpg");
        }
        if (Programe.equals("comp")){
            webView.loadUrl("file:///android_asset/comptree.jpg");
        }
    }
}