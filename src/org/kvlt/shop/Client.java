package org.kvlt.shop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Client {

    public Client(int id, String name, String number, String address, String card, String social, boolean edit) {
        try {
            Statement s = OrderManager.getDB().getConnection().createStatement();
            if (!edit) {
                ResultSet r = s.executeQuery("SELECT * FROM clients ORDER BY id DESC LIMIT 1");
                if (r.next()) id = r.getInt("id") + 1;
                else id = 1;
                String code = generateCode(id, number);
                s.execute(
                        "INSERT INTO clients " +
                                "(name, number, address, code, card, social) " +
                                "VALUES ('" + name + "', '" + number + "', '" + address + "', '" + code + "', '" + card + "', '" + social + "')"
                );
            } else {
                s.execute("UPDATE clients SET name='" + name + "'," +
                        "number='" + number + "', address='" + address + "'," +
                        "social='" + social + "' WHERE id=" + id);
            }
            s.close();
            OrderManager.getTableLoader().loadDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateCode(int id, String number) {
        String numberPart = number.length() >= 4 ? number.substring(number.length() - 4) : String.valueOf(new Random().nextInt(9999));
        return String.valueOf(id) + numberPart;
    }

}
