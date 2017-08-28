package org.kvlt.shop;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.kvlt.shop.org.kvlt.shop.utils.Log;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Client {

    public Client(int id, String name, String number, String address, String inviteCode, String card, String social, boolean edit) {
        try {
            Statement s = OrderManager.getDB().getConnection().createStatement();
            if (!edit) {

                ResultSet r = s.executeQuery("SELECT * FROM clients ORDER BY id DESC LIMIT 1");
                if (r.next()) {
                    id = r.getInt("id") + 1;
                } else {
                    id = 1;
                }

                String code = generateCode(id, number);

                s.execute(
                        "INSERT INTO clients " +
                                "(name, number, address, code, card, social) " +
                                "VALUES ('" + name + "', '" + number + "', '" + address + "', '" + code + "', '" + card + "', '" + social + "')"
                );
                //int newId = s.executeQuery("SELECT id FROM clients WHERE number='" + number + "'");
                ResultSet referal = s.executeQuery("SELECT * FROM clients WHERE code='" + inviteCode + "' LIMIT 1");
                int invId = referal.getInt("id");
                String oldRef = referal.getString("referals");
                String newReferals = genReferals(id, oldRef);

                s.execute("UPDATE clients SET referals='" + newReferals + "' WHERE id=" + invId);

            } else {
                s.execute("UPDATE clients SET name='" + name + "'," +
                        "number='" + number + "', address='" + address + "'," +
                        "social='" + social + "' WHERE id=" + id);
            }
            s.close();
            OrderManager.getTableLoader().loadDB();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Ошибка! Вероятно, указан неверный код.");
            Log.$("Всё пошло по пизде.\n" + e.getUnexpectedObject());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String genReferals(int id, String oldRef) throws NullPointerException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj;
        JSONArray refs = null;
        if (oldRef == null || oldRef.isEmpty()) {
            obj = new JSONObject();
            refs = new JSONArray();
            refs.add(0); /*HOTFIX*/
            refs.remove(0);
        } else {
            obj = (JSONObject) parser.parse(oldRef);
            refs = (JSONArray) obj.get("referals");
        }
        refs.add(id);
        obj.put("referals", refs);
        return obj.toJSONString();
    }

    private String generateCode(int id, String number) {
        String numberPart = number.length() >= 4 ? number.substring(number.length() - 4) : String.valueOf(new Random().nextInt(9999));
        return String.valueOf(id) + numberPart;
    }

}
