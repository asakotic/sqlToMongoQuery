package org.example.gui;

import lombok.Data;
import org.example.app.AppCore;

import javax.swing.*;
import java.awt.*;

@Data
public class MainFrame extends JFrame {

    private static MainFrame instance = null;
    private AppCore appCore;
    private JTable jTable;

    private MainFrame() {

    }

    public static MainFrame getInstance(){
        if (instance==null){
            instance=new MainFrame();
            instance.initialise();
        }
        return instance;
    }


    private void initialise() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jTable = new JTable();
        jTable.setPreferredScrollableViewportSize(new Dimension(500, 400));
        jTable.setFillsViewportHeight(true);
        this.add(new JScrollPane(jTable));

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

    public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
        this.jTable.setModel(appCore.getTableModel());
    }

}
