package jp.kanagawa.kawasaki.photokuri;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


public class PhotoKuri extends Activity {
	private Bitmap bitmapOrig = null;
    private Bitmap bitmapWip = null;
    private Bitmap bitmapOver = null;
    private Bitmap bitmapUnder = null;
    private Bitmap bitmapLaplac = null;
    private ImageView ivDisplay = null;
    
    static {
        System.loadLibrary("photokuri");
    }
    public native void convertToGray(Bitmap bitmapIn,Bitmap bitmapOut);
    public native void changeBrightness(int direction,Bitmap bitmap);
    public native void clearGray(Bitmap bitmapIn,Bitmap bitmapOut,Bitmap bitmapOver,Bitmap bitmapunder,Bitmap bitmaplaplac,double sgm);
    public native void genNoise(Bitmap bitmapIn,Bitmap bitmapOut);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_kuri);
        
        ivDisplay = (ImageView) findViewById(R.id.ivDisplay);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Config.ARGB_8888;
        bitmapOrig = BitmapFactory.decodeResource(this.getResources(),R.drawable.sample,options);
        bitmapWip = Bitmap.createBitmap(bitmapOrig.getWidth(),bitmapOrig.getHeight(),Config.ALPHA_8);
        if (bitmapOrig != null)
            ivDisplay.setImageBitmap(bitmapOrig);
    }
    
    public void onConvertToGray(View v) {
        convertToGray(bitmapOrig,bitmapWip);
        ivDisplay.setImageBitmap(bitmapWip);
    }
    public void onReset(View v) {
    	ivDisplay.setImageBitmap(bitmapOrig);
    }
    public void onClearGray(View v) {
    	EditText editxt = (EditText) findViewById(R.id.editText);
    	double sgm = Double.parseDouble(editxt.getText().toString());
    	
    	bitmapOver = Bitmap.createBitmap(bitmapOrig.getWidth(),bitmapOrig.getHeight(),Config.ALPHA_8);
    	bitmapUnder = Bitmap.createBitmap(bitmapOrig.getWidth(),bitmapOrig.getHeight(),Config.ALPHA_8);
    	bitmapLaplac = Bitmap.createBitmap(bitmapOrig.getWidth(),bitmapOrig.getHeight(),Config.ALPHA_8);
        clearGray(bitmapOrig,bitmapWip,bitmapOver,bitmapUnder,bitmapLaplac,sgm);
        ivDisplay.setImageBitmap(bitmapWip);
    }
    public void onGenNoise(View v) {
        genNoise(bitmapOrig,bitmapWip);
        ivDisplay.setImageBitmap(bitmapWip);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_photo_kuri, menu);
        return true;
    }

    
}
