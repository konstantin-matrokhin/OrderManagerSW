package org.kvlt.shop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBProvider {

    private String driver;
    private String tableName = "clients";

    private String url;
    private String host;
    private String port;
    private String db;
    private String user;
    private String password;

    private Connection conn;

    public DBProvider() {

        driver = OMSettings.$().getProperty("driver");
        db = OMSettings.$().getProperty("db");

        if (driver.equalsIgnoreCase("sqlite")) {
            db = "clients.db";
            url = "jdbc:sqlite:" + db;
        } else if (driver.equalsIgnoreCase("mysql")) {
            //TODO: mysql support
            url = "jdbc:mysql://" + host + ":" + port + "/" + db + "?user=" + user + "&password=" + password;
        }

        connect();
        initDB();
        testRow(); // TODO: remove
    }

    private void initDB() {
        try {
            conn.createStatement().executeQuery("" +
                    "CREATE TABLE IF NOT EXISTS [" + tableName + "] (\n" +
                    "[id] INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "[name] VARCHAR(100)  NOT NULL,\n" +
                    "[number] VARCHAR(15)  UNIQUE NOT NULL,\n" +
                    "[orders] TEXT  NULL,\n" +
                    "[address] VARCHAR(200)  NOT NULL,\n" +
                    "[referals] TEXT  NULL,\n" +
                    "[code] VARCHAR(32)  UNIQUE NOT NULL,\n" +
                    "[card] VARCHAR(24) UNIQUE NOT NULL,\n" +
                    "[social] VARCHAR(64)  NULL\n" +
                    ")").close();
        } catch (Exception e) {

        }
    }

    private void testRow() {
        try {
            conn.createStatement().executeQuery(
                    "INSERT INTO [clients] (id, name, number, address, code) VALUES" +
                    "(1, 'toster username', '8768532423423', 'default city', '5001ab')").close();
        } catch (Exception e) {

        }
    }

    private void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            Log.$("База данных подключена!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public boolean query(String q) {
        try {
            Statement s = conn.createStatement();
            return s.execute(q);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
