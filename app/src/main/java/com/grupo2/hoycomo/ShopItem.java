package com.grupo2.hoycomo;

public class ShopItem {
    private String shopName;
    private String shopPic;
    private String desc1;
    private String desc2;
    private boolean favorite;
    private Integer ranking;
    private Integer id;

    public ShopItem(String shopName, String shopPic, String desc1, String desc2, boolean favorite, Integer ranking, Integer id) {
        this.shopName = shopName;
        this.shopPic = shopPic;
        this.desc1 = desc1;
        this.desc2 = desc2;
        this.favorite = favorite;
        this.ranking = ranking;
        this.id = id;

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

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
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


}
