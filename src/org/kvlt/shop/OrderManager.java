package org.kvlt.shop;

public class OrderManager {

    private static DBProvider db;
    private static TableLoader tableLoader;

    static {
        db = new DBProvider();
        tableLoader = new TableLoader();
    }

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
