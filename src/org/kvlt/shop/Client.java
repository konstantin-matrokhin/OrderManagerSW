package org.kvlt.shop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Client {

    public Client(String name, String number, String address, String social) {
        try {
            Statement s = OrderManager.getDB().getConnection().createStatement();
            int id;
            ResultSet r = s.executeQuery("SELECT * FROM clients ORDER BY id DESC LIMIT 1");
            if (r.next()) id = r.getInt("id") + 1; else id = 0;
            String code = generateCode(id, number);
            s.execute(
                    "INSERT INTO clients " +
                            "(name, number, address, code, social) " +
                            "VALUES ('" + name + "', '" + number + "', '" + address + "', '" + code + "', '" + social + "')"
            );
            s.close();
            OrderManager.getTableLoader().loadDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateCode(int id, String number) {
        String numberPart = number.length() >= 4 ? number.substring(number.length() - 4) : "";
        return String.valueOf(id) + numberPart;
    }

}
