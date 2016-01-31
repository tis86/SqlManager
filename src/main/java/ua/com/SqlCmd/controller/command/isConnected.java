package ua.com.SqlCmd.controller.command;

import ua.com.SqlCmd.controller.command.Command;
import ua.com.SqlCmd.model.dbManager;
import ua.com.SqlCmd.view.View;

/**
 * Created by Тарас on 31.01.2016.
 */
public class isConnected implements Command {

    private dbManager manager;
    private View view;

    public isConnected(dbManager manager, View view) {

        this.manager = manager;
        this.view = view;
    }

    public boolean canProcess(String command) {
        return !manager.isConnected();
    }

    public void process(String command) {
        view.write(String.format("for first time you need to use command " +
                "connect|databaseName|userName|password, not '%s'", command));
    }
}
