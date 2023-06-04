package org.example.app;

import org.example.gui.MainFrame;
import org.example.sql.SQLParser;
import org.example.sql.SQLValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        AppCore appCore = new AppCore();
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.setAppCore(appCore);
    }
}