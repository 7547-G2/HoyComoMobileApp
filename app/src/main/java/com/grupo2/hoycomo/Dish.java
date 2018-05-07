package com.grupo2.hoycomo;

public class Dish {
    private Integer dId;
    private String dName;
    private String dImage;
    private Integer dPrice;
    private Integer dOrder;

    public Dish(Integer dId, String dName, String dImage, Integer dPrice, Integer dOrder) {
        this.dId = dId;
        this.dName = dName;
        this.dImage = dImage;
        this.dPrice = dPrice;
        this.dOrder = dOrder;
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdImage() {
        return dImage;
    }

    public void setdImage(String dImage) {
        this.dImage = dImage;
    }

    public Integer getdPrice() {
        return dPrice;
    }

    public void setdPrice(Integer dPrice) {
        this.dPrice = dPrice;
    }

    public Integer getdOrder() {
        return dOrder;
    }

    public void setdOrder(Integer dOrder) {
        this.dOrder = dOrder;
    }
}
