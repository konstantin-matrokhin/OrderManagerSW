package org.kvlt.shop;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Client {

    public Client(String name, String number, String address, String social) {
        String code = String.valueOf(new Random().nextInt(999999));
        try {
            Statement s = OrderManager.getDB().getConnection().createStatement();
            s.execute(
                    "INSERT INTO clients " +
                            "(name, number, address, code, social) " +
                            "VALUES ('" + name + "', '" + number + "', '" + address + "', '" + code + "' , '" + social + "')"
            );
            OrderManager.getTableLoader().loadDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
