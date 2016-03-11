package com.itheima.login;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
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
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itheima.Asynchttpclient.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

public class SearchNearbyActivity extends Activity {
    private EditText et;
	//private TextView tv_html;
	private ListView lv;
	private List<Store> storeLists;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchnearby);
            
		et = (EditText) findViewById(R.id.et);
		lv = (ListView) findViewById(R.id.lv);
		//initListData();

        //tv_html = (TextView)findViewById(R.id.tv_html);
	}
	
	
	
	public void clickSearchNearby(View v) {
		     String range = et.getText().toString().trim();   
			 initListData(range);
	
	}
	
	
	
		private void initListData(final String range) {
		new Thread() {

			public void run() {

				try {
					String path = "http://waterping.com:8080/api/yelp/nearBy/" + range +"/"; 
	
					URL url = new URL(path);

					// [2.3]拿到httpurlconnection对象 用于发送或者接收数据
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					// [2.4]设置发送get请求
					conn.setRequestMethod("GET");// get要求大写 默认就是get请求
					// [2.5]设置请求超时时间
					conn.setConnectTimeout(5000);
					// [2.6]获取服务器返回的状态码
					int code = conn.getResponseCode();
					// [2.7]如果code == 200 说明请求成功
					if (code == 200) {
						// [2.8]获取服务器返回的数据 是以流的形式返回的
						InputStream in = conn.getInputStream();
						String str = StreamTools.readStream(in);
						JSONObject json = new JSONObject(str);  
						storeLists = ParseJSONutils.ParseJSONnearby(json);

						System.out.println("storeLists:"+storeLists.size());
						
						runOnUiThread(new  Runnable() {
							public void run() {
								//[3]更新ui  把数据展示到listview上 
								lv.setAdapter(new MyAdapter());
								
							}
						});
						

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			};
		}.start();

	}
	
	

	//定义数据适配器
			private class MyAdapter extends BaseAdapter {
	            @Override
				public int getCount() {
	            	Log.d("Calling getCount ", "called");
	            	return storeLists.size();
				}

				@Override
				public Object getItem(int position) {
					return null;
				}

				@Override
				public long getItemId(int position) {
					return 0;
				}

				@Override
				public View getView(int position, View convertView, ViewGroup parent) {
					Log.d("Calling getView ", "called");
	            	
					View view;
					if (convertView == null) {
						view = View.inflate(getApplicationContext(), R.layout.item,
								null);
					} else {
						view = convertView;
	
					}
					
					
					SmartImageView iv_icon = (SmartImageView) view.findViewById(R.id.iv_icon);
					TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
					TextView tv_rating = (TextView) view.findViewById(R.id.tv_rating);
					TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);
                    
					//展示图片
//					iv_icon.setImageUrl(imageUrl);
					//iv_icon.setImageUrl(imageUrl, R.drawable.bg);
					
					// 显示数据
					tv_name.setText(storeLists.get(position).getName());
					tv_rating.setText(String.valueOf(storeLists.get(position).getRating()));
					tv_phone.setText(storeLists.get(position).getPhone());
					iv_icon.setImageUrl(storeLists.get(position).getImage_url());
											

return view;
				}
			}
	

}

	

