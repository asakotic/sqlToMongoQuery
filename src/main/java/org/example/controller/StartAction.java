package org.example.controller;

import org.example.data.Row;
import org.example.database.MongoDBQuery;
import org.example.gui.MainFrame;
import org.example.gui.table.TableModel;
import org.example.sql.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class StartAction extends AbstractAction {

    private String text;

    @Override
    public void actionPerformed(ActionEvent e) {
        text = MainFrame.getInstance().getTaQuery().getText();
        MainFrame.getInstance().getLblMessage().setText("");
        MongoDBQuery db = SQLToMongo.convertSQL(text);
        List<Row> rows = db.executeOnDatabase(MongoDBController.getConnection());
        MainFrame.getInstance().getJTable().setModel(new TableModel(rows));
    }
}
