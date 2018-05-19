package com.grupo2.hoycomo;

public class OrderItem {
    private Integer orderId;
    private Integer storeId;
    private String storeName;
    private String status;

    public OrderItem(Integer orderId, Integer storeId, String storeName, String status) {
        this.orderId = orderId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.status = status;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
