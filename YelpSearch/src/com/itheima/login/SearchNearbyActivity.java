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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import android.os.Bundle;
import android.util.Base64;
import android.app.Activity;
import android.net.ParseException;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itheima.Asynchttpclient.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class SearchNearbyActivity extends Activity {

	
	private EditText et;
	private TextView tv_html;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchnearby);

		
		et = (EditText) findViewById(R.id.et);
		tv_html = (TextView)findViewById(R.id.tv_html);
	}
	
    //通过输入range搜索附近 
	//Json解析 根据http://waterping.com:8080/ 里的格式
	
	public void clickSearchNearby(View v) {
		String range = et.getText().toString().trim(); 
		String path =  "http://waterping.com:8080/api/yelp/nearBy/" + range + "/";
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(path, new AsyncHttpResponseHandler(){
            //请求成功的回调
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				try{	
					 String str = new String (responseBody);
					 log(str);  
				     JSONObject json = new JSONObject(str);  
				    //把   latitude_delta 从json中解析出来  其他类似
				     log("Int from JSONArray from JSON Object \t"  
			                + json.getJSONObject("yelpSearchNearByResponse").getJSONObject("data").getJSONObject("region").getJSONObject("span").getInt("latitude_delta"));  
				        
				     int lag = json.getJSONObject("yelpSearchNearByResponse").getJSONObject("data").getJSONObject("region").getJSONObject("span").getInt("latitude_delta");  
				    //显示 latitude_delta，for test, 实际上不需要
				        tv_html.setText(String.valueOf(lag));
				}  catch (Exception e){
				    	e.printStackTrace();
				    }
				
				
				//Toast.makeText(getApplicationContext(), new String(responseBody), 1).show();
			   	//tv_html.setText(new String(responseBody));
		  }
		
			//请求失败的回调
			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				Toast.makeText(getApplicationContext(), "NOresponse", 1).show();
				
			}
		});	
		
	}
	

	private static void log(Object obj) {  
        System.out.println(obj.toString());  
    }
	
	
}




//try {
//JSONObject json = new JSONObject(str);
//json.getJSONArray(str);
//JSONObject response = array.getJSONObject(0);
//String res = response.getString("test");
//System.out.println(res);
//tv_html.setText(res);
//} catch (JSONException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}



/*
 try {
		response = (JSONObject) parser.parse(str);
 	String test = (String)response.get("test");
		System.out.println(test);
		//tv_html.setText(test);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		Toast.makeText(getApplicationContext(), "parse fail", 1).show();
	}
*/		

/*
String searchResponseJSON = responseBody.toString();
JSONParser parser = new JSONParser();
JSONObject response = null;

 try {
		response = (JSONObject) parser.parse(searchResponseJSON);
	    int total = response.getInt("total");
		System.out.println(total);
		tv_html.setText(String.valueOf(total));
*/		
//		JSONArray businesses = (JSONArray) response.get("businesses");
//		JSONObject firstBusiness = (JSONObject) businesses.get(0);
//		String firstBusinessID = firstBusiness.get("id").toString();
//		System.out.println(firstBusinessID);
		//tv_html.setText(firstBusinessID);

 

// System.out.println(String.format(
//     "%s businesses found, querying business info for the top result \"%s\" ...",
//     businesses.size(), firstBusinessID));
//
 // Select the first business and display business details
// String businessResponseJSON = yelpApi.searchByBusinessId(firstBusinessID.toString());
// System.out.println(String.format("Result for business \"%s\" found:", firstBusinessID));
// System.out.println(businessResponseJSON);
