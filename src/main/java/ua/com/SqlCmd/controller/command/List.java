package ua.com.SqlCmd.controller.command;

import ua.com.SqlCmd.model.JDBCManager;
import ua.com.SqlCmd.model.dbManager;
import ua.com.SqlCmd.view.View;

import java.util.Arrays;

/**
 * Created by Тарас on 29.01.2016.
 */
public class List implements Command {

    private dbManager manager;
    private View view;

    public List(View view, dbManager manager) {
        this.view = view;
        this.manager = manager;
    }

    public boolean canProcess(String command) {
        return command.equals("list");
    }

    public void process(String command) {
        String[] tableNames = manager.getTableNames();

        String message = Arrays.toString(tableNames);

        view.write(message);
    }
}
