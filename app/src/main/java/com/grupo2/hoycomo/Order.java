package com.grupo2.hoycomo;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private Integer id;
    private List<DishItem> dishItemList;
    private Integer total;
    private String address;

    public Order(Integer id, Integer total, String address) {
        this.id = id;
        this.dishItemList = new ArrayList<>();
        this.total = total;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addDish(DishItem d){
        if (!hasDish(d.getDish_id())){
            dishItemList.add(d);
        }
    }

    private boolean hasDish(Integer dish_id) {
        DishItem aux;
        Boolean result = false;
        for (int i=0; i<dishItemList.size(); i++){
            aux = dishItemList.get(i);
            if (aux.getDish_id() == dish_id){
                return result;
            }
        }
        return result;
    }
}
