package ua.com.SqlCmd.controller.command;

import ua.com.SqlCmd.model.dbManager;
import ua.com.SqlCmd.view.View;

/**
 * Created by Тарас on 31.01.2016.
 */
public class Connect implements Command {

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
        try {
            String[] data = command.split("\\|");
            if (data.length != 4) { //TODO remove magic number 4
                throw new IllegalArgumentException("Неверн количество параметров разделенных знаков" +
                        " '|', ожидается 4, но реальное количество:  " + data.length);
            }
            String databaseName = data[1];
            String userName = data[2];
            String password = data[3];

            manager.connect(databaseName, userName, password);
            view.write("Подключение к базе данных успешно!");
        } catch (Exception e) {
            printError(e);
        }
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        if (e.getCause() != null) {
            message += " " + e.getCause().getMessage();
        }
        view.write("Неудачное подключение. Причина: " + message);
        view.write("Попробуйте еще раз");
    }
}
