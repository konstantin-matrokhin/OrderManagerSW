package org.kvlt.shop;

import org.kvlt.shop.org.kvlt.shop.gui.ButtonColumn;
import org.kvlt.shop.org.kvlt.shop.gui.OrdersForm;
import org.kvlt.shop.org.kvlt.shop.gui.ReferralsForm;
import org.kvlt.shop.org.kvlt.shop.gui.TableForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableLoader {

    private static final Object[] TITLES = {
            "ID",
            "Имя",
            "Тел.",
            "Заказы",
            "Адрес",
            "Приглашенные",
            "Код",
            "Номер карты",
            "Соц. сеть"
    };

    private static final int REFERRAL_BUTTON_COLUMN = 3;
    private static final int ORDERS_BUTTON_COLUMN  = 5;
    private static final int SOCIAL_BUTTON_COLUMN  = 8;

    private TableForm tableForm;
    private DBProvider db;
    private DefaultTableModel model;

    public TableLoader() {
        tableForm = new TableForm();
        db = OrderManager.getDB();

        initTable();
        initBtnActions();
        loadDB();

        ClientSearch clientSearch = new ClientSearch(tableForm);

        tableForm.getBtnSearch().addActionListener(clientSearch.listener());
        tableForm.getBtnClear().addActionListener(clientSearch.clearListener());
        tableForm.getTablePane().registerKeyboardAction(clientSearch.listener(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void initBtnActions() {
        new ButtonColumn(tableForm.getTable(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = (Integer) getTableForm().getTable().getValueAt(getTableForm().getTable().getSelectedRow(), 0);
                OrdersForm form = new OrdersForm(id);
                form.pack();
                form.setVisible(true);

            }
        }, REFERRAL_BUTTON_COLUMN);
        new ButtonColumn(tableForm.getTable(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = (Integer) getTableForm().getTable().getValueAt(getTableForm().getTable().getSelectedRow(), 0);
                ReferralsForm refForm = new ReferralsForm(id);
                refForm.pack();
                refForm.setVisible(true);
            }
        }, ORDERS_BUTTON_COLUMN);

        new ButtonColumn(tableForm.getTable(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String link = (String) getTableForm().getTable().getValueAt(
                        getTableForm().getTable().getSelectedRow(), SOCIAL_BUTTON_COLUMN);
                if (link.isEmpty()) return;

                try {
                    if (!link.startsWith("https://") || !link.startsWith("http://")) {
                        link = "http://" + link;
                    }
                    Desktop.getDesktop().browse(new URL(link).toURI());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, SOCIAL_BUTTON_COLUMN);

    }

    private void initTable() {
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                return (   col == REFERRAL_BUTTON_COLUMN
                        || col == ORDERS_BUTTON_COLUMN
                        || col == SOCIAL_BUTTON_COLUMN);
            }
        };

        tableForm.getTable().setModel(model);
        model.setColumnIdentifiers(TITLES);
    }

    public TableForm getTableForm() {
        return tableForm;
    }

    public void loadDB() {
        SwingUtilities.invokeLater(() -> {
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
                    String referrals = "Посмотреть";
                    String code = r.getString("code");
                    String card = r.getString("card");
                    String social = r.getString("social");

                    model.addRow(new Object[] {
                            id, name, number, orders, address, referrals, code, card, social
                    });
                }
                r.close();
                s.close();
                TableColumnModel columnModel = getTableForm().getTable().getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(10);
                columnModel.getColumn(1).setPreferredWidth(150);
                //TODO: remove all logs
                /*Log.$("База данных обновлена!");*/
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

}
