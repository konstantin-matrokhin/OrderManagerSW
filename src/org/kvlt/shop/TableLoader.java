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
        db = OrderManager.getDB();
        initTable();
        initBtnActions();
        loadDB();
        tableForm.getBtnSearch().addActionListener(new ClientSearch(tableForm).listener());
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
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        //model = (DefaultTableModel) tableForm.getTable().getModel();
        tableForm.getTable().setModel(model);
        model.setColumnIdentifiers(titles);
    }

    public TableForm getTableForm() {
        return tableForm;
    }

    public void loadDB() {
        model.setRowCount(0);
        try {
            Statement s = db.getConnection().createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM clients ORDER BY id DESC");
            while (r.next()) {
                int id = r.getInt("id");
                String name = r.getString("name");
                String number = r.getString("number");
                String orders = "Посмотреть";
                String address = r.getString("address");
                String referals = "Посмотреть";
                String code = r.getString("code");
                String social = r.getString("social");

                model.addRow(new Object[] {
                        id, name, number, orders, address, referals, code, social
                });
            }
            r.close();
            s.close();
            Log.$("База данных обновлена!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
