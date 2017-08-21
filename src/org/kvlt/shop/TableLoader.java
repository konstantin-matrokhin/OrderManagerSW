package org.kvlt.shop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableLoader {

    private TableForm tableForm;
    private DBProvider db;
    private DefaultTableModel model;

    private Object[] titles = {
            "ID",
            "Имя",
            "Тел.",
            "Заказы",
            "Адрес",
            "Рефералы",
            "Код",
            "Соц. сеть"
    };

    public TableLoader() {
        tableForm = new TableForm();
        db = new DBProvider();
        initTable();
        initBtnActions();
        loadDB();
    }

    private void initBtnActions() {
        ButtonColumn viewOrders = new ButtonColumn(tableForm.getTable(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }, 3);
        ButtonColumn viewReferals = new ButtonColumn(tableForm.getTable(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }, 5);
    }

    private void initTable() {
        model = (DefaultTableModel) tableForm.getTable().getModel();
        model.setColumnIdentifiers(titles);
    }

    private void loadDB() {
        try {
            Statement s = db.getConnection().createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM clients");
            while (r.next()) {
                int id = r.getInt("id");
                String name = r.getString("name");
                String number = r.getString("number");
                String orders = "Посмотреть";/*r.getString("orders");*/
                String address = r.getString("address");
                String referals = "Посмотреть";/*r.getString("referals");*/
                String code = r.getString("code");
                String social = r.getString("social");

                model.addRow(new Object[] {
                        id, name, number, orders, address, referals, code, social
                });
            }
            Log.$("База данных загружена!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
