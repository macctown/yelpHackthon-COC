package com.itheima.login;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.itheima.Asynchttpclient.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class MainActivity extends Activity {

	
	private EditText et;
	private TextView tv_html;

	 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
//		et = (EditText) findViewById(R.id.et);
//		tv_html = (TextView)findViewById(R.id.tv_html);
	}

	public void click1(View v) {
		 Intent i = new Intent(MainActivity.this, SearchNearbyActivity.class); 
         startActivityForResult(i, 0);
		}
	
	public void click2(View v) {
		 Intent i = new Intent(MainActivity.this, SearchStoreIDActivity.class);
        startActivityForResult(i, 0);
		}

 
	}
