package org.kvlt.shop;

public class OrderManager {

    private static DBProvider db = new DBProvider();
    private static TableLoader tableLoader = new TableLoader();

    public static DBProvider getDB() {
        return db;
    }

    public static TableLoader getTableLoader() {
        return tableLoader;
    }

    public static void main(String args[]) {
        new OrderManager();
    }

}
