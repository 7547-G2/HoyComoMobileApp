package com.grupo2.hoycomo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MenuCateg {
    private Integer cId;
    private String cName;
    private Integer cOrder;
    private List<Dish> cList;

    public MenuCateg(Integer cId, String cName, Integer cOrder) {
        this.cId = cId;
        this.cName = cName;
        this.cOrder = cOrder;
        this.cList = new ArrayList<>();
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Integer getcOrder() {
        return cOrder;
    }

    public void setcOrder(Integer cOrder) {
        this.cOrder = cOrder;
    }

    public List<Dish> getcList() {
        return cList;
    }

    public void setcList(List<Dish> cList) {
        this.cList = cList;
    }

    public void addItem(Dish item){
        this.cList.add(item);
    }

    public void sortDishes(){
        Collections.sort(this.cList,new Comparator<Dish>(){
            public int compare(Dish d1,Dish d2){
                //return d1.getdId() - d2.getdId();
                if (d1.getdId() > d2.getdId()){
                    return 1;
                } else if (d1.getdId() < d2.getdId()){
                    return -1;
                } else {
                    return 0;
                }
            }});
    }
}
