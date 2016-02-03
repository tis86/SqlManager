package SqlCmd.controller;

import SqlCmd.model.JDBCManager;
import SqlCmd.model.dbManager;
import SqlCmd.view.Console;
import SqlCmd.view.View;

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
