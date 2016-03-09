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
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.itheima.Asynchttpclient.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class MainActivity extends Activity {

	
	private EditText et_url;
	private TextView tv_html;

	 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		et_url = (EditText) findViewById(R.id.et_url);
		tv_html = (TextView)findViewById(R.id.tv_html);
	}

	public void click1(View v) {
		String path = et_url.getText().toString().trim(); 
		
		//创建client
		AsyncHttpClient client = new AsyncHttpClient();
		//get请求
		client.get(path, new AsyncHttpResponseHandler(){
            //请求成功的回调
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				//Toast.makeText(getApplicationContext(), new String(responseBody), 1).show();
				tv_html.setText(new String(responseBody));
			}
			//请求失败的回调
			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			}
			
			
		});	
		
	}
	

	
	
	
}
