package com.grupo2.hoycomo;

public class DishItem {

    private Integer dish_id;
    private Integer sum;
    private Integer subTotal;

    public DishItem(Integer dish_id, Integer sum, Integer subTotal) {
        this.dish_id = dish_id;
        this.sum = sum;
        this.subTotal = subTotal;
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
}

