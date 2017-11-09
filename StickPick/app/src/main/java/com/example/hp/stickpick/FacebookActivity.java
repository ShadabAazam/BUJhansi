package com.example.hp.stickpick;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class FacebookActivity extends AppCompatActivity {

    ProgressDialog dialog;
    String dataUrl = "http://facebook.com/shadabazam.it";
    String buUrl="https://www.bujhansi.ac.in/index.aspx";
    String resultUrl="https://exam.bujhansi.ac.in/frmViewCampusCurrentResult.aspx";
    String stackOverUrl="http://stackoverflow.com/questions/tagged/java";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        webView();
        //makeRequest();
    }
    WebView webView;
    void webView() {
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        if (HomeActivity.checkBuFb==1){
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            //getSupportActionBar().setTitle("University Website");
            webView.loadUrl(buUrl);
        }
        else if (HomeActivity.checkBuFb==2){
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            //getSupportActionBar().setTitle("FaceBook");
            webView.loadUrl(dataUrl);
        }
        else if(HomeActivity.checkBuFb==3) {
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            //getSupportActionBar().setTitle("Current Campus Result");
            webView.loadUrl(resultUrl);
        }
        else if(HomeActivity.checkBuFb==4) {
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            //getSupportActionBar().setTitle("Current Campus Result");
            webView.loadUrl(stackOverUrl);
        }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            WebSettings webSettings = view.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setSupportZoom(true);
            webSettings.setBuiltInZoomControls(true);
            return true;
        }
    }

    @Override public void onBackPressed() {

        if(webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void makeRequest() {
        if (Networking.isConnected(this)) {
            new GetData().execute();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }
    class GetData extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(FacebookActivity.this);
            dialog.setMessage("Please wait ...");
            dialog.setCancelable(false);
            dialog.show();
        }
        @Override
        protected String doInBackground(Void... params) {
            Networking networking = new Networking();
            String data = null;
            try {

                data = networking.getDataFromCloud(dataUrl);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }
        @Override
        protected void onPostExecute(String data) {
            dialog.dismiss();
        }
    }
}
