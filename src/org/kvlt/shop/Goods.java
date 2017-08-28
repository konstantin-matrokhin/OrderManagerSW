package org.kvlt.shop;

import org.json.simple.JSONObject;

public class Goods {

    private String name;
    private double cost;
    private int article;
    private String discount;
    private GoodsType type;

    public Goods(String name, double cost, int article, String discount, GoodsType type) {
        this.name = name;
        this.cost = cost;
        this.article = article;
        this.discount = discount;
        this.type = type;
    }

    public void save() {
        JSONObject j = new JSONObject();
        j.put("name", name);
        j.put("cost", cost);
        j.put("article", article);
        j.put("discount", discount);
        j.put("type", type.toString());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getArticle() {
        return article;
    }

    public void setArticle(int article) {
        this.article = article;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public GoodsType getType() {
        return type;
    }

    public void setType(GoodsType type) {
        this.type = type;
    }
}
