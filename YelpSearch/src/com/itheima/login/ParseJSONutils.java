package com.itheima.login;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;


import android.util.Xml;

public class ParseJSONutils {

		public static List<Store> ParseJSONnearby(JSONObject json) throws Exception {
			List<Store> storeLists =  new ArrayList<Store>();
			//int total =json.getJSONObject("yelpSearchNearByResponse").getJSONObject("data").getInt("total");
			
			for (int i = 0; i < json.getJSONObject("yelpSearchNearByResponse").getJSONObject("data").getJSONArray("businesses").length();i++){
				JSONObject businessObject = json.getJSONObject("yelpSearchNearByResponse").getJSONObject("data").getJSONArray("businesses").getJSONObject(i);
				Store store = new Store();
			    store.setName(businessObject.getString("name"));
			    store.setRating(businessObject.getInt("rating"));
			    store.setPhone(businessObject.getString("phone"));
			    store.setImage_url(businessObject.getString("image_url"));
				
				storeLists.add(store);
			}			
					
					
					
			return storeLists;
		}
	}
