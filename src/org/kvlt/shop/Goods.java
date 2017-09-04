package org.kvlt.shop;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Goods {

    private String name;
    private double cost;
    private int article;
    private String discount;
    private GoodsType type;
    private int id;

    public Goods(int id, String name, double cost, int article, String discount, GoodsType type) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.article = article;
        this.discount = discount;
        this.type = type;
    }

    public void save() {
        JSONObject j = new JSONObject();
        JSONArray arr = null;
        String oldOrders;
        try {
            oldOrders = OrderManager.getDB().getConnection().createStatement().executeQuery("" +
                    "SELECT orders FROM clients WHERE id=" + id).getString("orders");

            arr = oldOrders.isEmpty() ? new JSONArray() : (JSONArray) new JSONParser().parse(oldOrders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject tObj = new JSONObject();
        tObj.put("name", name);
        tObj.put("cost", cost);
        tObj.put("article", article);
        tObj.put("discount", discount);
        tObj.put("type", type.toString());
        arr.add(tObj);
        String orders = arr.toJSONString();
        OrderManager.getDB().query("UPDATE clients SET orders='" + orders + "' WHERE id=" + id);
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
