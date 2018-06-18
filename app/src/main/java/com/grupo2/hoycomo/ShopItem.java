package com.grupo2.hoycomo;

public class ShopItem {
    private String shopName;
    private String shopPic;
    private String tipoId;
    private String tipoName;
    private String leadTime;
    private String minPrice;
    private String maxPrice;
    private boolean favorite;
    private Integer ranking;
    private Integer id;
    private Integer desc;

    public ShopItem(String shopName, String shopPic, String tipoId, String tipoName, String leadTime, String minPrice, String maxPrice, boolean favorite, Integer ranking, Integer id, Integer desc) {
        this.shopName = shopName;
        this.shopPic = shopPic;
        this.tipoId = tipoId;
        this.tipoName = tipoName;
        this.leadTime = leadTime;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.favorite = favorite;
        this.ranking = ranking;
        System.out.println("rankimg en stHopItem: " + ranking);
        this.id = id;
        this.desc = desc;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopPic() {
        return shopPic;
    }

    public void setShopPic(String shopPic) {
        this.shopPic = shopPic;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(String leadTime) {
        this.leadTime = leadTime;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getTipoId() {
        return tipoId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    public String getTipoName() {
        return tipoName;
    }

    public void setTipoName(String tipoName) {
        this.tipoName = tipoName;
    }

    public Integer getDesc() {
        return desc;
    }

    public void setDesc(Integer desc) {
        this.desc = desc;
    }
}
