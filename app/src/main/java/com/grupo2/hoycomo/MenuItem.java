package com.grupo2.hoycomo;

public class MenuItem {
    private Integer dishId;
    private String dishName;
    private Integer dishPrice;
    private boolean category;

    public MenuItem(String dishName, Integer dishPrice, boolean category) {
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.category = category;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Integer getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(Integer dishPrice) {
        this.dishPrice = dishPrice;
    }

    public boolean isCategory() {
        return category;
    }

    public void setCategory(boolean category) {
        this.category = category;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }
}
