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
        System.out.println("has order: " + storeId);
        for (int i=0; i<orderList.size(); i++){
            aux = orderList.get(i);
            System.out.println("bucle: " + i + " " + aux.getId());
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
                System.out.print("se borro la orden de la tienda: " + storeId);
            }
        }
    }
    /*
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }
    */

    public void addDish(DishItem di){
        System.out.println("addDish:" + di.getStore_id());
        System.out.println("tamaño de la lista de ordenes: " + orderList.size());
        Order aux;
        Boolean has = false;
        for (int i=0; i<orderList.size(); i++){
            aux = orderList.get(i);
            if (aux.getId() == di.getStore_id()){
                System.out.println("tamaño de la lista de platos: " + aux.getDishItemList().size());
                aux.addDish(di);
                has = true;
                this.deleteOrder(di.getStore_id());
                this.addOrder(aux);
                System.out.println("addDish l tienda ya tiene ordenes");
            }
        }
        if (!has) {
            aux = new Order();
            aux.addDish(di);
            System.out.println("addDish --" + di.getStore_id());
            aux.setId(di.getStore_id());
            this.addOrder(aux);
            System.out.println("addDish se agrega nueva orden");
        }
    }

    public void deleteDish(DishItem di){
        System.out.println("deleteDish:" + di.getStore_id());
        Order aux;
        for (int i=0; i<orderList.size(); i++){
            aux = orderList.get(i);
            if (aux.getId() == di.getStore_id()){
                aux.deleteDish(di);
                System.out.println("deleteDish se borra plato");
            }
        }
    }

    public Order getOrder(Integer id){
        Order aux;
        Order aux2 = null;
        for (int i=0; i<orderList.size(); i++){
            aux = orderList.get(i);
            if (aux.getId() == id){
                aux2 = aux;
            }
        }
        return aux2;
    }

}
