package com.grupo2.hoycomo;

public class OrderItem {
    private Integer orderId;
    private Integer storeId;
    private String storeName;
    private String status;
    private String fecha;

    public OrderItem(Integer orderId, Integer storeId, String storeName, String status, String fecha) {
        this.orderId = orderId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.status = status;
        this.fecha = fecha;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
