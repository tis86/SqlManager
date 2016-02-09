package SqlCmd.controller.command;

import SqlCmd.model.DataView;
import SqlCmd.model.dbManager;
import SqlCmd.view.View;

/**
 * Created by Тарас on 09.02.2016.
 */
public class Clear implements Command {

    private dbManager manager;
    private View view;

    public Clear(dbManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if(data.length != 2) {
            throw new IllegalArgumentException("Нужно вводить 'clear|tableName', а не: " + command);
        }

        manager.clear(data[1]);

        view.write(String.format("Таблица %s очищена", data[1]));
    }
}
