package org.example.gui;

import lombok.Data;
import lombok.Getter;
import org.example.controller.StartAction;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;

@Data
@Getter
public class MainFrame extends JFrame {

    private static MainFrame instance = null;
    private JTable jTable;
    private JButton btnStart;
    private RSyntaxTextArea taQuery;
    private JLabel lblMessage;
    private RSyntaxTextArea userText;

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
        taQuery = new RSyntaxTextArea();
        taQuery.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
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
        this.add(new RTextScrollPane(taQuery), BorderLayout.CENTER);
        this.add(new JScrollPane(jTable), BorderLayout.SOUTH);

        //this.pack();
        //this.setLocationRelativeTo(null);
        this.setVisible(true);

        btnStart.addActionListener(new StartAction());
    }

}
