package SqlCmd.controller.command;

import SqlCmd.view.View;

/**
 * Created by Тарас on 29.01.2016.
 */
public class Help implements Command{

    private View view;

    public Help(View view) {
        this.view = view;
    }

    public boolean canProcess(String command) {
        return command.equals("help");
    }

    public void process(String command) {
        view.write("Command list: ");

        view.write("\tconnect|databaseName|userName|password - ");
        view.write("\t\t connect to database");

        view.write("\tlist - ");
        view.write("\t\t table list database");

        view.write("\thelp - ");
        view.write("\t\t write about all commands in sqlManager");

        view.write("\texit - ");
        view.write("\t\t exit from sqlManager");

        view.write("\tfind|tableName - ");
        view.write("\t\t get all data from 'table tableName'");
    }
}
