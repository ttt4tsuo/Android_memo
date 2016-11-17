package com.example.mac1k3h4.hellowandroid;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {
    //http://qiita.com/calciolife/items/39b2696a9a03e8591d40
    /*@Override
    protected void onCreate() {
        super.onCreate();
        Log.d("myapp", "Activity:onCreate()");
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("myapp", "Activity:onCreate(Bundle)");

        Button btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent();
                String pac;
                pac=getResources().getString(R.string.pac_name);
                String cls;
                cls=getResources().getString(R.string.cls_name);
                ComponentName component = new ComponentName(pac, cls);
                it.setAction(Intent.ACTION_VIEW);
                it.putExtra("ext", 100);
                it.setComponent(component);
                startActivity(it);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myapp", "Activity:onStart()");
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("myapp", "Activity:onRestoreInstanceState()");
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.d("myapp", "Activity:onPostCreate()");
    }
    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        Log.d("myapp", "Activity:onNewIntent()");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myapp", "Activity:onRestart()");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myapp", "Activity:onResume()");
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d("myapp", "Activity:onPostResume()");
    }
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.d("myapp", "Activity:onUserLeaveHint()");
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("myapp", "Activity:onSaveInstanceState(Bundle)");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d("myapp", "Activity:onPause()");
    }
    /*@Override
    protected void onCreateThumbnail(Bitmap outBitmap){
        super.onCreateThumbnail(outBitmap);
        Log.d("myapp", "Activity:onPause()");
    }*/
    @Override
    protected void onStop(){
        super.onStop();
        Log.d("myapp", "Activity:onStop()");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("myapp", "Activity:onDestroy()");
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
