package com.grupo2.hoycomo;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private Integer id;
    private List<DishItem> dishItemList;
    private Integer total;
    private String address;

    public Order() {
        this.dishItemList = new ArrayList<>();
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

    public List<DishItem> getDishItemList() {
        return dishItemList;
    }

    public void addDish(DishItem d){
        if (!hasDish(d.getDish_id())){
            System.out.println("se agrega plato en orden");
            dishItemList.add(d);
        }
    }

    public void deleteDish(DishItem d){
        DishItem aux;
        for (int i=0; i<dishItemList.size(); i++){
            aux = dishItemList.get(i);
            if (aux.getDish_id() == d.getDish_id()){
                dishItemList.remove(i);
            }
        }
    }

    public boolean hasDish(Integer dish_id) {
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
