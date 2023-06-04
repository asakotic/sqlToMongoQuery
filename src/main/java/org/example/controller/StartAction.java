package org.example.controller;

import org.example.gui.MainFrame;
import org.example.sql.Clause;
import org.example.sql.SQLParser;
import org.example.sql.SQLValidator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class StartAction extends AbstractAction {

    private String text;

    @Override
    public void actionPerformed(ActionEvent e) {
        text = MainFrame.getInstance().getTaQuery().getText();
        MainFrame.getInstance().getLblMessage().setText("");

        SQLParser parser = new SQLParser(text);
        SQLValidator validator = new SQLValidator(text);

        if(!validator.checkSQL1()) {
            MainFrame.getInstance().getLblMessage().setText("Syntax error");
            return;
        }

        List<Clause> clauses = parser.solve();

        if(!validator.checkSQL2(clauses))
            MainFrame.getInstance().getLblMessage().setText("error 2");

        if(!validator.checkSQL3(clauses))
            MainFrame.getInstance().getLblMessage().setText("error 3");

        if(!validator.checkSQL4(clauses))
            MainFrame.getInstance().getLblMessage().setText("error 4");

    }
}
