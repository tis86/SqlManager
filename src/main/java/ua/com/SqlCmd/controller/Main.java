package ua.com.SqlCmd.controller;

import ua.com.SqlCmd.model.JDBCManager;
import ua.com.SqlCmd.model.dbManager;
import ua.com.SqlCmd.view.Console;
import ua.com.SqlCmd.view.View;

/**
 * Created by Тарас on 26.01.2016.
 */
public class Main {
    public static void main(String[] args) {
        View view = new Console();
        dbManager manager = new JDBCManager();

        MainController controller = new MainController(view, manager);
        controller.run();
    }
}
