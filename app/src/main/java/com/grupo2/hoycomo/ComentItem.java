package com.grupo2.hoycomo;

public class ComentItem {

    private Integer rating;
    private String date;
    private String coment;
    private String replica;
    private String dateRep;
    private String store;

    public ComentItem(Integer rating, String date, String coment, String dateRep, String replica, String store) {
        this.rating = rating;
        this.date = date;
        this.coment = coment;
        this.dateRep = dateRep;
        this.replica = replica;
        this.store = store;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public String getReplica() {
        return replica;
    }

    public void setReplica(String replica) {
        this.replica = replica;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getDateRep() {
        return dateRep;
    }

    public void setDateRep(String dateRep) {
        this.dateRep = dateRep;
    }
}
