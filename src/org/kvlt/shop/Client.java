package org.kvlt.shop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Client {

    public Client(String name, String number, String address, String social) {
        try {
            Statement s = OrderManager.getDB().getConnection().createStatement();
            s.execute(
                    "INSERT INTO clients " +
                            "(name, number, address, code, social) " +
                            "VALUES ('" + name + "', '" + number + "', '" + address + "', '00000', '" + social + "')"
            );
            ResultSet r = s.executeQuery("SELECT id FROM clients WHERE number='" + number +"'");
            int id = r.getInt("id");
            String code = generateCode(id, number);
            s.execute("UPDATE clients SET code='" + code + "' WHERE id='" + id + "'");
            s.close();
            OrderManager.getTableLoader().loadDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateCode(int id, String number) {
        String numberPart = number.length() >= 4 ? number.substring(number.length() - 4) : "";
        return id + numberPart;
    }

}
