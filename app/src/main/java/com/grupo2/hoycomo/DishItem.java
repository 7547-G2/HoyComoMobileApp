package com.grupo2.hoycomo;

public class DishItem {

    private Integer dish_id;
    private Integer store_id;
    private String dish_name;
    private Integer sum;
    private Integer subTotal;
    private String obs;

    public DishItem(Integer store_id, Integer dish_id, String dish_name, Integer sum, Integer subTotal, String obs) {
        this.store_id = store_id;
        this.dish_id = dish_id;
        this.dish_name = dish_name;
        this.sum = sum;
        this.subTotal = subTotal;
        this.obs = obs;
    }

    public Integer getDish_id() {
        return dish_id;
    }

    public void setDish_id(Integer dish_id) {
        this.dish_id = dish_id;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }
}

