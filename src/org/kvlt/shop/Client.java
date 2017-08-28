package org.kvlt.shop;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Client {

    private static final String REFERRALS_ARRAY = "referrals";
    private static final int PHONE_LAST_DIGITS_AMOUNT = 4;

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

                s.execute("INSERT INTO clients\n" +
                                "(name, number, address, code, card, social)\n" +
                                "VALUES ('" + name + "', '" + number + "', '" + address + "', '" + code + "', '" + card + "', '" + social + "')"
                );

                if (!inviteCode.trim().isEmpty()) {
                    ResultSet referal = s.executeQuery("SELECT * FROM clients WHERE code='" + inviteCode + "' LIMIT 1");
                    if (referal.next()) {
                        int invId = referal.getInt("id");
                        String oldRef = referal.getString("referrals");
                        String newReferals = genReferrals(id, oldRef);

                        s.execute("UPDATE clients SET referrals='" + newReferals + "' WHERE id=" + invId);
                        referal.close();
                    }
                }
            } else {
                s.execute("UPDATE clients SET name='" + name + "'," +
                        "number='" + number + "', address='" + address + "'," +
                        "social='" + social + "' WHERE id=" + id);
            }

            s.close();
            OrderManager.getTableLoader().loadDB();

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Ошибка! Вероятно, указан неверный код.");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //TODO: fix warnings
    private String genReferrals(int id, String oldRef) throws NullPointerException, ParseException {
        JSONObject obj;
        JSONArray refs;

        if (oldRef == null || oldRef.isEmpty()) {
            obj = new JSONObject();
            refs = new JSONArray();
            refs.add(0); /*HOTFIX*/
            refs.remove(0);
        } else {
            obj = (JSONObject) new JSONParser().parse(oldRef);
            refs = (JSONArray) obj.get(REFERRALS_ARRAY);
        }

        refs.add(id);
        obj.put(REFERRALS_ARRAY, refs);
        return obj.toJSONString();
    }

    private String generateCode(int id, String number) {
        String numberPart = (number.length() >= PHONE_LAST_DIGITS_AMOUNT)
                ? number.substring(number.length() - PHONE_LAST_DIGITS_AMOUNT)
                : String.valueOf(new Random().nextInt(9999));

        return id + numberPart;
    }

}
