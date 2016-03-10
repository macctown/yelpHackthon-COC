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
import org.json.JSONException;
import org.json.JSONObject;

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

public class SearchStoreIDActivity extends Activity {

	
	private EditText et_searchID;
	private TextView tv_searchID;

	 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchstoreid);

		
		et_searchID = (EditText) findViewById(R.id.et_searchID);
		tv_searchID = (TextView)findViewById(R.id.tv_searchID);
	}

	//按照ID搜索  example： yelp-san-francisco
	//Json解析 根据http://waterping.com:8080/ 里的格式
	
	
	public void clickSearchStoreID(View v) {
		String storeID = et_searchID.getText().toString().trim(); 
		String path = "http://waterping.com:8080/api/yelp/getBusinessbyId/" + storeID + "/";
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(path, new AsyncHttpResponseHandler(){
            //请求成功的回调
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				 try {
					String str = new String (responseBody);
					 JSONObject json = new JSONObject(str);  
					 int rating = json.getJSONObject("yelpSearchNearByResponse").getJSONObject("data").getInt("rating");  
					 tv_searchID.setText(String.valueOf(rating));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			//请求失败的回调
			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			}
			
			
		});	
		
	}
}
