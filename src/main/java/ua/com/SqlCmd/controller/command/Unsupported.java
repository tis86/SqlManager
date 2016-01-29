package ua.com.SqlCmd.controller.command;

import ua.com.SqlCmd.controller.command.Command;
import ua.com.SqlCmd.view.View;

/**
 * Created by Тарас on 29.01.2016.
 */
public class Unsupported implements Command {

    private View view;

    public Unsupported(View view) {
        this.view = view;
    }

    public boolean canProcess(String command) {
        return true;
    }

    public void process(String command) {
        view.write("'" + command + "' is not recognized as an internal or external command");
    }
}
