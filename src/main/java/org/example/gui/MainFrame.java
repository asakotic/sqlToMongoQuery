package org.example.gui;

import lombok.Data;
import lombok.Getter;
import org.example.app.AppCore;
import org.example.controller.StartAction;

import javax.swing.*;
import java.awt.*;

@Data
@Getter
public class MainFrame extends JFrame {

    private static MainFrame instance = null;
    private AppCore appCore;
    private JTable jTable;
    private JButton btnStart;
    private JTextArea taQuery;
    private JLabel lblMessage;

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

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = (int) screenSize.getHeight();
        int screenWidth = (int) screenSize.getWidth();
        this.setSize(screenWidth / 2, screenHeight / 2 + 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("BP");

        JPanel panel = new JPanel();
        JPanel mini = new JPanel();
        BorderLayout bl= new BorderLayout();
        this.setLayout(bl);
        lblMessage = new JLabel();
        btnStart = new JButton("Submit");
        taQuery = new JTextArea("Write something");

        mini.add(lblMessage);
        mini.add(btnStart);
       // panel.add(mini);
       // panel.add(taQuery);


        jTable = new JTable();
        //jTable.setPreferredScrollableViewportSize(new Dimension(500, 400));
        jTable.setFillsViewportHeight(true);
        //this.add(new JScrollPane(jTable));
        //jTable.setSize(900,900);
        //JScrollPane scrollpane = new JScrollPane(jTable);
        //panel.add(scrollpane);
        //panel.add(jTable,bl);
        this.add(mini,BorderLayout.NORTH);
        this.add(taQuery,BorderLayout.CENTER);
        this.add(new JScrollPane(jTable), BorderLayout.SOUTH);

        //this.pack();
        //this.setLocationRelativeTo(null);
        this.setVisible(true);

        btnStart.addActionListener(new StartAction());
    }

    public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
        this.jTable.setModel(appCore.getTableModel());
    }



}
