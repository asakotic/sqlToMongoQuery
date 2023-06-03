package org.example.controller;

import org.example.gui.MainFrame;
import org.example.sql.SQLParser;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class StartAction extends AbstractAction {

    private String text;

    @Override
    public void actionPerformed(ActionEvent e) {
        text = MainFrame.getInstance().getTaQuery().getText();

        SQLParser parser = new SQLParser(text);
        parser.solve();

    }
}
