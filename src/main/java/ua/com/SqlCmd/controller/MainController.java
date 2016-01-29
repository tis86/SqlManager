package ua.com.SqlCmd.controller;

import ua.com.SqlCmd.controller.command.*;
import ua.com.SqlCmd.model.dbManager;
import ua.com.SqlCmd.view.View;

/**
 * Created by Тарас on 26.01.2016.
 */
public class MainController {

    private Command[] commands;
    private View view;
    private dbManager manager;

    public MainController(View view, dbManager manager) {
        this.view = view;
        this.manager = manager;
        this.commands = new Command[]{new Exit(view), new Help(view),
                new List(view, manager), new Find(view, manager), new Unsupported(view)};
    }

    public void run() {
        connectToDb();

        doCommand();
    }

    private void doCommand() {
        while (true) {
            view.write("Введи команду: ");
            String input = view.read();

            for (Command command : commands) {
                if (command.canProcess(input)) {
                    command.process(input);
                    break;
                }

            }
        }
    }

    private void connectToDb() {
        view.write("Привет, введи название базы данных, имя пользователя" +
                "и пароль в формате: database|userName|password");
        while (true) {
            try {
                String string = view.read();
                String[] data = string.split("\\|");
                if (data.length != 3) {
                    throw new IllegalArgumentException("Неверн количество параметров разделенных знаков" +
                            " '|', ожидается 3, но реальное количество:  " + data.length);
                }
                String databaseName = data[0];
                String userName = data[1];
                String password = data[2];

                manager.connect(databaseName, userName, password);
                break;
            } catch (Exception e) {
                printError(e);
            }

        }

        view.write("Подключение к базе данных успешно!");
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
