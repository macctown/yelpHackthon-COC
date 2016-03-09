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

	//�����߳��ж���һ��handler 
	private Handler handler = new Handler(){
		//��������������߳�����ִ�е� 
		public void handleMessage(android.os.Message msg) {
			
			//���ԾͿ��������߳��������ui�� 
			
			
			//[1]����һ�·��͵���������Ϣ 
			
			switch (msg.what) {
			case REQUESTSUCESS:   //��������ɹ�
				String content =  (String) msg.obj;
				tv_reuslt.setText(content);
				break;
			case REQUESTNOTFOUND:   //��������ɹ�
				Toast.makeText(getApplicationContext(), "������Դ������", 0).show();
				
				break;
				
			case REQUESTEXCEPTION:   //��������ɹ�
				Toast.makeText(getApplicationContext(), "������æ ���Ժ�....", 1).show();
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
		// [1]�ҵ����ǹ��ĵĿؼ�
		
		et_path = (EditText) findViewById(R.id.et_path);
		tv_reuslt = (TextView) findViewById(R.id.tv_result);
		
		//[1.1]��ӡ��ǰ�̵߳����� 
		System.out.println("��ǰ�߳�����:"+Thread.currentThread().getName());
		
		
		
		
	}

	
	//[2]�����ť���в鿴 ָ��·����Դ�� 
	public void click(View v) {
		//[2.0]����һ�����߳� 
		new Thread(){public void run() {
			
			
			try {
				//[2.1]��ȡԴ��·�� 
				String path = et_path.getText().toString().trim();
				//[2.2]����URL ����ָ������Ҫ���ʵ� ��ַ(·��)
				URL url = new URL(path);
				
				//[2.3]�õ�httpurlconnection����  ���ڷ��ͻ��߽������� 
				HttpURLConnection  conn = (HttpURLConnection) url.openConnection();
				//[2.4]���÷���get���� 
				conn.setRequestMethod("GET");//getҪ���д  Ĭ�Ͼ���get����
				//[2.5]��������ʱʱ��
				conn.setConnectTimeout(5000);
				//[2.6]��ȡ���������ص�״̬�� 
				int code = conn.getResponseCode();
				//[2.7]���code == 200 ˵������ɹ�
				if (code == 200) {
					//[2.8]��ȡ���������ص�����   ����������ʽ���ص�  ���ڰ���ת�����ַ�����һ���ǳ������Ĳ���  �����ҳ��һ��������(utils)
					InputStream in = conn.getInputStream(); 
					
					//[2.9]ʹ�����Ƕ���Ĺ����� ��inת����String
					String content = StreamTools.readStream(in);
					
					//2.9.0 ����message���� 
					Message msg = new Message();
					msg.what = REQUESTSUCESS;
					msg.obj = content;
					
					//2.9.1 �������Ǵ�����handler(����) ����ϵͳ ˵��Ҫ����ui
					handler.sendMessage(msg);  //����һ����Ϣ  ��Ϣ(msg)������ݷŵ���msg�� handleMessage�����ͻ�ִ��
					
					//[2.9.1]�������������չʾ��textview ��  ��仰�����ڸ���ui���߼�
//					tv_reuslt.setText(content);
					
				}else{
					//������Դ������  Toast��һ��view Ҳ���������̸߳���ui
					Message msg = new Message();
					msg.what = REQUESTNOTFOUND;//����������Ϣ
					handler.sendMessage(msg);
					
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				Message msg = new Message();
				msg.what = REQUESTEXCEPTION;//����������Ϣ
				handler.sendMessage(msg);  //������Ϣ
				
				
			}
			
			
		};}.start();
		
		
		
	}
	
}
