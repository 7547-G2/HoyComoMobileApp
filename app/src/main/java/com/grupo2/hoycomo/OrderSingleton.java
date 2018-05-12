package com.grupo2.hoycomo;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samsung on 30/03/2018.
 */

public class OrderSingleton {
    private static OrderSingleton mAppSingletonInstance;
    private static Context mContext;
    private static List<Order> orderList;

    private OrderSingleton(Context context) {
        mContext = context;
    }

    public static synchronized OrderSingleton getInstance(Context context) {
        if (mAppSingletonInstance == null) {
            mAppSingletonInstance = new OrderSingleton(context);
            orderList = new ArrayList<>();
        }
        return mAppSingletonInstance;
    }

    public void addOrder(Order o){
        orderList.add(o);
    }

    public boolean hasOrder(Integer storeId){
        Order aux;
        Boolean result = false;
        for (int i=0; i<orderList.size(); i++){
            aux = orderList.get(i);
            if (aux.getId() == storeId){
                result = true;
            }
        }
        return result;
    }

    public void deleteOrder(Integer storeId){
        Order aux;
        for (int i=0; i<orderList.size(); i++){
            aux = orderList.get(i);
            if (aux.getId() == storeId){
                orderList.remove(i);
            }
        }
    }
    /*
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }
    */

}
