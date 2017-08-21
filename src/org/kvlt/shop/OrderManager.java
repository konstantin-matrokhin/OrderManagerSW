package org.kvlt.shop;

public class OrderManager {

    private static DBProvider db = new DBProvider();;

    private OrderManager() {
        new TableLoader();
    }

    public static DBProvider getDB() {
        return db;
    }

    public static void main(String args[]) {
        new OrderManager();
    }

}
