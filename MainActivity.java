package com.kitakutt.webviewdebug;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            WebView wv= (WebView)findViewById(R.id.web_view);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.setWebViewClient(new WebViewClient() {
                public void doUpdateVisitedHistory(WebView view, String url, boolean isReload){}
                public void onFormResubmission(WebView view, Message dontResend, Message resend) {}
                public void onLoadResource(WebView view, String url){}
                public void onPageCommitVisible(WebView view, String url){}
                public void onPageFinished(WebView view, String url){}
                public void onPageStarted(WebView view, String url, Bitmap favicon){}
                public void onReceivedClientCertRequest(WebView view, ClientCertRequest request){}

                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){}
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){}
                public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm){}
                public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse){}
                public void onReceivedLoginRequest(WebView view, String realm, String account, String args){}
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){}
                public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail){return true;}
                //public void onSafeBrowsingHit(WebView view, WebResourceRequest request, int threatType, SafeBrowsingResponse callback){}
                public void onScaleChanged(WebView view, float oldScale, float newScale){}
                public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg){}
                public void onUnhandledKeyEvent(WebView view, KeyEvent event){}
                public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request){return null;}
                public WebResourceResponse shouldInterceptRequest(WebView view, String url){return null;}
                public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event){return true;}
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){return true;}
                public boolean shouldOverrideUrlLoading(WebView view, String url){return true;}

            });
            wv.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {

                }
            });

            wv.loadUrl("http://www.yahoo.co.jp");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
