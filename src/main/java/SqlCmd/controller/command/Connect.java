package SqlCmd.controller.command;

import SqlCmd.model.dbManager;
import SqlCmd.view.View;

/**
 * Created by Тарас on 31.01.2016.
 */
public class Connect implements Command {

    private static String COMMAND_SAMPLE = "connect|sqlcmd|postgres|postgres";

    private dbManager manager;
    private View view;

    public Connect(dbManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length != count()) {
            throw new IllegalArgumentException(String.format("Неверн количество параметров разделенных знаков" +
                    " '|', ожидается %s, но реальное количество:  %s", count(), data.length));
        }

        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];

        manager.connect(databaseName, userName, password);
        view.write("Подключение к базе данных успешно!");
    }

    private int count() {
        return COMMAND_SAMPLE.split("\\|").length;
    }


}
