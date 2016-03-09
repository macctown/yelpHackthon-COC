package com.itheima.look;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	protected  final int REQUESTSUCESS = 0;  //ctrl + shift + X  Y
	protected  final int REQUESTNOTFOUND = 1;
	protected  final int REQUESTEXCEPTION = 2;
	
	private EditText et_path;
	private TextView tv_reuslt;

	//在主线程中定义一个handler 
	private Handler handler = new Handler(){
		//这个方法是在主线程里面执行的 
		public void handleMessage(android.os.Message msg) {
			
			//所以就可以在主线程里面更新ui了 
			
			
			//[1]区分一下发送的是哪条消息 
			
			switch (msg.what) {
			case REQUESTSUCESS:   //代表请求成功
				String content =  (String) msg.obj;
				tv_reuslt.setText(content);
				break;
			case REQUESTNOTFOUND:   //代表请求成功
				Toast.makeText(getApplicationContext(), "请求资源不存在", 0).show();
				
				break;
				
			case REQUESTEXCEPTION:   //代表请求成功
				Toast.makeText(getApplicationContext(), "服务器忙 请稍后....", 1).show();
				break;
			default:
				break;
			}
			
			
		};
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// [1]找到我们关心的控件
		
		et_path = (EditText) findViewById(R.id.et_path);
		tv_reuslt = (TextView) findViewById(R.id.tv_result);
		
		//[1.1]打印当前线程的名字 
		System.out.println("当前线程名字:"+Thread.currentThread().getName());
		
		
		
		
	}

	
	//[2]点击按钮进行查看 指定路径的源码 
	public void click(View v) {
		//[2.0]创建一个子线程 
		new Thread(){public void run() {
			
			
			try {
				//[2.1]获取源码路径 
				String path = et_path.getText().toString().trim();
				//[2.2]创建URL 对象指定我们要访问的 网址(路径)
				URL url = new URL(path);
				
				//[2.3]拿到httpurlconnection对象  用于发送或者接收数据 
				HttpURLConnection  conn = (HttpURLConnection) url.openConnection();
				//[2.4]设置发送get请求 
				conn.setRequestMethod("GET");//get要求大写  默认就是get请求
				//[2.5]设置请求超时时间
				conn.setConnectTimeout(5000);
				//[2.6]获取服务器返回的状态码 
				int code = conn.getResponseCode();
				//[2.7]如果code == 200 说明请求成功
				if (code == 200) {
					//[2.8]获取服务器返回的数据   是以流的形式返回的  由于把流转换成字符串是一个非常常见的操作  所以我抽出一个工具类(utils)
					InputStream in = conn.getInputStream(); 
					
					//[2.9]使用我们定义的工具类 把in转换成String
					String content = StreamTools.readStream(in);
					
					//2.9.0 创建message对象 
					Message msg = new Message();
					msg.what = REQUESTSUCESS;
					msg.obj = content;
					
					//2.9.1 拿着我们创建的handler(助手) 告诉系统 说我要更新ui
					handler.sendMessage(msg);  //发了一条消息  消息(msg)里把数据放到了msg里 handleMessage方法就会执行
					
					//[2.9.1]把流里面的数据展示到textview 上  这句话就属于更新ui的逻辑
//					tv_reuslt.setText(content);
					
				}else{
					//请求资源不存在  Toast是一个view 也不能在在线程更新ui
					Message msg = new Message();
					msg.what = REQUESTNOTFOUND;//代表哪条消息
					handler.sendMessage(msg);
					
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				Message msg = new Message();
				msg.what = REQUESTEXCEPTION;//代表哪条消息
				handler.sendMessage(msg);  //发送消息
				
				
			}
			
			
		};}.start();
		
		
		
	}
	
}
