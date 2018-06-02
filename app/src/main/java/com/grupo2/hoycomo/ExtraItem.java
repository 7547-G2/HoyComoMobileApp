package com.grupo2.hoycomo;

public class ExtraItem {

    private Integer dishId;
    private Integer extraId;
    private String extraName;
    private Integer extraPrice;
    private Boolean selected;

    ExtraItem(Integer dishId, Integer extraId, String extraName, Integer extraPrice) {
        this.dishId = dishId;
        this.extraId = extraId;
        this.extraName = extraName;
        this.extraPrice = extraPrice;
        this.selected = false;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public Integer getExtraId() {
        return extraId;
    }

    public void setExtraId(Integer extraId) {
        this.extraId = extraId;
    }

    public String getExtraName() {
        return extraName;
    }

    public void setExtraName(String extraName) {
        this.extraName = extraName;
    }

    public Integer getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(Integer extraPrice) {
        this.extraPrice = extraPrice;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
