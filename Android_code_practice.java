package com.example.jsonparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.net.InetAddress;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener(){
        	@Override
        	public void onClick(View v){
        		Toast.makeText(MainActivity.this, "タッチ", Toast.LENGTH_LONG).show();
        		/*
        		try{
        			InputStream inputStream = getResources().getAssets().open("sample.json");
            		int size = inputStream.available();
            	    byte[] buffer = new byte[size];
            	    inputStream.read(buffer);
            	    inputStream.close();
            	    String str = new String(buffer);
            	    
            	    new Thread(new Runnable(){
            	    	@Override
            	    	public void run() {
            	    		String url = "http://www.google.co.jp";
            	    		HttpGet getMethod = new HttpGet(url);
            	    		DefaultHttpClient client = new DefaultHttpClient();
            	    		try{
            	    		HttpResponse response = client.execute(getMethod);
            	    		Log.d("myapp",Integer.toString(response.getStatusLine().getStatusCode()));
            	    		}catch(IOException e){
            	    			
            	    		}
            	    	}
            	    }).start();
        
        			JSONObject jsonObject = new JSONObject(str);
        			JSONArray jsonArray = jsonObject.getJSONArray("predictions");
        			for (int i = 0; i < jsonArray.length(); i++) {
        				JSONObject jsonOneRecord = jsonArray.getJSONObject(i);
        				Log.d("myapp",jsonOneRecord.getString("description"));
        			}
        			
        		}catch (IOException e){
        			Log.d("myapp","io error");
        		}catch(JSONException e){
        			Log.d("myapp","json error");
        		}*/
        		
        		//Volley
        		/*final RequestQueue mRequestQueue;
        		String url = "https://api.tokyometroapp.jp/api/v2/datapoints?rdf:type=odpt:Station&acl:consumerKey=19c5e4d142ee7f353b8e9338ece7a95e4bb3892940bab46a6ebe3f5c8ae8a3e2";
        		mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        		mRequestQueue.add(new JsonObjectRequest(Method.GET, url, null,
        			    new Listener<JSONObject>() {
        			        @Override
        			        public void onResponse(JSONObject response) {
        			            // JSONObjectのパース、List、Viewへの追加等
        			        }
        			    },
        			 
        			    new Response.ErrorListener() {
        			        @Override public void onErrorResponse(VolleyError error) {
        			            // エラー処理 error.networkResponseで確認
        			            // エラー表示など
        			        }
        			    }));
        		mRequestQueue.start();*/

        
        
        new Thread(new Runnable(){
	    	@Override
	    	public void run() {
        	try{
        		//https://opensrs.com/docs/integration/Java_Example.htm
        		//http://docs.oracle.com/javase/jp/6/technotes/guides/security/jsse/JSSERefGuide.html
        		
        		SSLContext mSSLContext = SSLContext.getInstance ("SSL");
        		mSSLContext.init(null, null, null);
        		/** 
        		 * HttpsURLConnectionを使用した場合、sslsocketで直接通信する選択肢もある http://ash.jp/java/java_http.htm
        		 *//*
        		URL url = new URL("https://www.google.co.jp");
        		HttpsURLConnection mSSLConn = (HttpsURLConnection) url.openConnection();
        		mSSLConn.setRequestMethod("GET");
        		mSSLConn.setDefaultSSLSocketFactory(mSSLContext.getSocketFactory());
        		mSSLConn.connect();
        		Log.d("myapp",mSSLContext.getProtocol() );
        		Log.d("myapp",mSSLConn.getHeaderFields().get(null).toString());
        		*/
        		/**
        		 * defaulthttpclient を使用した場合
        		 * http://blog.zaq.ne.jp/oboe2uran/article/634/
        		 */
        		//http://stackoverflow.com/questions/5206010/using-apache-httpclient-for-https
        		final javax.net.ssl.SSLSocketFactory socketfactory = mSSLContext.getSocketFactory();
        		Scheme scheme = new Scheme("https",
        			new LayeredSocketFactory(){
        			@Override
                    public Socket createSocket(Socket socket,String host,int port,boolean autoClose) throws IOException,UnknownHostException{
        				SSLSocket sslsock = (SSLSocket)socketfactory.createSocket(socket,host,port,autoClose);
        				String[] sslv3 = {"SSLv3"};
                       	sslsock.setEnabledProtocols(sslv3);
        				return sslsock;
                    }
                    @Override
                    public Socket connectSocket(Socket sock,String host,int port,InetAddress localAddress,int localPort,HttpParams params) throws IOException,UnknownHostException,ConnectTimeoutException{
                    	SSLSocket sslsock = (SSLSocket)((sock != null) ? sock : createSocket());
                    	for(String proto: sslsock.getEnabledProtocols()){
                    		Log.d("myapp",proto);
                    	}
                       	String[] sslv3 = {"SSL"};
                       	sslsock.setEnabledProtocols(sslv3);
                       	if (localAddress != null || localPort > 0){
                       		InetSocketAddress isa = new InetSocketAddress(localAddress,localPort);
                       		sslsock.bind(isa);
                       	}
                       int connTimeout = HttpConnectionParams.getConnectionTimeout(params);
                       int soTimeout = HttpConnectionParams.getSoTimeout(params);
                       InetSocketAddress remoteAddress;
                       remoteAddress = new InetSocketAddress(host,port);
                       sslsock.connect(remoteAddress, connTimeout);
                       sslsock.setSoTimeout(soTimeout);
                       return sslsock;
                    }
                    @Override
                    public Socket createSocket() throws IOException{
                    	Log.d("myapp","creat()!");
                       return socketfactory.createSocket();
                    }
                    @Override
                    public boolean isSecure(Socket sock) throws IllegalArgumentException{
                       return true;
                    }
        		}
        		,443);
        		HttpClient mHttpClient = new DefaultHttpClient();
        		mHttpClient.getConnectionManager().getSchemeRegistry().register(scheme);
        		HttpGet mHttpGet = new HttpGet("https://www.google.co.jp");
        	    HttpResponse httpResponse = mHttpClient.execute(mHttpGet);
        		Log.d("myapp",Integer.toString(httpResponse.getStatusLine().getStatusCode()));
        		
        	} catch (NoSuchAlgorithmException e){
        	} catch (KeyManagementException e){
        	} catch (IOException e){	
        	}
	    	}//try end
        	}).start();//run end
        	}
        });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
