package elmir.vip.individualproject.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import elmir.vip.individualproject.R;

public class AboutDubaiActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_uae);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.pBar_visitingDubai);

        WebView webView = findViewById(R.id.webView_visitingDubai);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new AboutDubaiActivity.WebViewClient());
        webView.loadUrl("https://www.expo2020dubai.com/en/support/visiting-uae/visiting-dubai");

    }
    public class WebViewClient extends android.webkit.WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

}
