package org.kvlt.shop;

import org.kvlt.shop.org.kvlt.shop.gui.TableForm;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionListener;

public class ClientSearch {

    private TableForm tableForm;
    private TableRowSorter<TableModel> sorter;
    private String searchText;

    public ClientSearch(TableForm tableForm) {
        this.tableForm = tableForm;
        sorter = new TableRowSorter<>(this.tableForm.getTable().getModel());

        this.tableForm.getTable().setRowSorter(sorter);
    }

    public ActionListener listener() {
        return e -> {
            searchText = tableForm.getFieldSearch().getText();
            if (searchText.isEmpty()) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
            }
        };
    }

    public ActionListener clearListener() {
        return e -> {
            sorter.setRowFilter(null);
            tableForm.getFieldSearch().setText("");
        };
    }

}
