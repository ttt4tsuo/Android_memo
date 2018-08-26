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
            wv.getSettings().setAppCacheEnabled(true);
            wv.setWebViewClient(new WebViewClient() {
                @Override
                public void doUpdateVisitedHistory(WebView view, String url, boolean isReload){
                    super.doUpdateVisitedHistory(view,url,isReload);
                }
                @Override
                public void onFormResubmission(WebView view, Message dontResend, Message resend) {
                    super.onFormResubmission(view,dontResend,resend);
                }
                @Override
                public void onLoadResource(WebView view, String url){
                    super.onLoadResource(view,url);
                }
                @Override
                public void onPageCommitVisible(WebView view, String url){
                    super.onPageCommitVisible(view,url);
                }
                @Override
                public void onPageFinished(WebView view, String url){
                    super.onPageFinished(view,url);
                }
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon){
                    super.onPageStarted(view,url,favicon);
                }
                @Override
                public void onReceivedClientCertRequest(WebView view, ClientCertRequest request){
                    super.onReceivedClientCertRequest(view,request);
                }
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
                    super.onReceivedError(view,errorCode,description,failingUrl);
                }
                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
                    super.onReceivedError(view,request,error);
                }
                @Override
                public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm){
                    super.onReceivedHttpAuthRequest(view,handler,host,realm);
                }
                @Override
                public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse){
                    super.onReceivedHttpError(view,request,errorResponse);
                }
                @Override
                public void onReceivedLoginRequest(WebView view, String realm, String account, String args){
                    super.onReceivedLoginRequest(view,realm,account,args);
                }
                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                    super.onReceivedSslError(view,handler,error);
                }
                @Override
                public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail){
                    return super.onRenderProcessGone(view,detail);
                }
                //public void onSafeBrowsingHit(WebView view, WebResourceRequest request, int threatType, SafeBrowsingResponse callback){}
                @Override
                public void onScaleChanged(WebView view, float oldScale, float newScale){
                    super.onScaleChanged(view,oldScale,newScale);
                }
                @Override
                public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg){
                    super.onTooManyRedirects(view,cancelMsg,continueMsg);
                }
                @Override
                public void onUnhandledKeyEvent(WebView view, KeyEvent event){
                    super.onUnhandledKeyEvent(view,event);
                }
                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request){
                    return super.shouldInterceptRequest(view,request);
                }
                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, String url){
                    return super.shouldInterceptRequest(view,url);
                }
                @Override
                public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event){
                    return super.shouldOverrideKeyEvent(view,event);
                }
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
                    return super.shouldOverrideUrlLoading(view,request);
                }
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url){
                    return super.shouldOverrideUrlLoading(view,url);
                }

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
