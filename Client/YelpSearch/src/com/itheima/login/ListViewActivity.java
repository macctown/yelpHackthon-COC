package com.itheima.login;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.itheima.Asynchttpclient.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewActivity extends Activity{
	private ListView lv;
	private List<Store> storeLists;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
      
		lv = (ListView) findViewById(R.id.lv);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();

		final List<Store> storeLists = (List<Store>)bundle.getSerializable("storeLists");
		new Thread() {
           public void run() {

					System.out.println("storeLists:"+storeLists.size());
					runOnUiThread(new  Runnable() {
						public void run() {
							//[3]更新ui  把数据展示到listview上 
							lv.setAdapter(new MyAdapter());
						}
					});
	        }
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
					
					
//					SmartImageView iv_icon = (SmartImageView) view.findViewById(R.id.iv_icon);
					TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
//					TextView tv_rating = (TextView) view.findViewById(R.id.tv_rating);
//					TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);

					//String imageUrl = storeLists.get(position).getImage_url();
					
					//展示图片
//					iv_icon.setImageUrl(imageUrl);
					//iv_icon.setImageUrl(imageUrl, R.drawable.bg);
					
					// 显示数据
					tv_name.setText(storeLists.get(position).getName());
//					tv_rating.setText(String.valueOf(storeLists.get(position).getRating()));
//					tv_phone.setText(storeLists.get(position).getPhone());
//					
					
					
//					String typee = newsLists.get(position).getType();
//					String comment = newsLists.get(position).getComment();
//					int type = Integer.parseInt(typee);
//					switch (type) {
//					case 1:
	//
//						tv_type.setText(comment+"国内");
//						break;
//					case 2:
//						tv_type.setText("跟帖");
//						break;
//					case 3:
//						tv_type.setText("国外");
//						break;
	//
//					default:
//						break;
//					}

					return view;
				}
			}
	

}
