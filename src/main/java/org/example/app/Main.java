package org.example.app;

import org.example.gui.MainFrame;

public class Main {
    public static void main(String[] args) {
        AppCore appCore = new AppCore();
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.setAppCore(appCore);

        appCore.readDataFromTable("departments");
    }
}