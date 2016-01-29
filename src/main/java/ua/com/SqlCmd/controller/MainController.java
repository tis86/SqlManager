package ua.com.SqlCmd.controller;

import ua.com.SqlCmd.controller.command.Command;
import ua.com.SqlCmd.controller.command.Exit;
import ua.com.SqlCmd.controller.command.Help;
import ua.com.SqlCmd.model.DataView;
import ua.com.SqlCmd.model.dbManager;
import ua.com.SqlCmd.view.View;

import java.sql.SQLException;
import java.util.Arrays;

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
        this.commands = new Command[] { new Exit(view), new Help(view)};
    }

    public void run() {
        connectToDb();

        doCommand();
    }

    private void doCommand() {
        while (true) {
            view.write("Введи команду: ");
            String command = view.read();

            if (command.equals("list")) {
                doList();
            } else if (commands[1].canProcess(command)) {
                commands[1].process(command);
            } else if (commands[0].canProcess(command)) {
                commands[0].process(command);
            } else if (command.startsWith("find|")) {
                doFind(command);
            } else {
                view.write("'" + command + "' is not recognized as an internal or external command");
            }
        }
    }

    private void doFind(String command) {
        String[] data = command.split("\\|");
        String tableName = data[1];

        String[] tableColumnns = manager.getTableColumns(tableName);
        printHeader(tableColumnns);

        DataView[] tableData = manager.getTableData(tableName);
        printTable(tableData);
    }

    private void printTable(DataView[] tableData) {

        for (DataView row : tableData) {
            printRow(row);
        }
    }

    private void printRow(DataView row) {
        Object[] values = row.getValues();
        String result = "|";
        for (Object value : values) {
            result += value + " | ";
        }
        view.write(result);
    }

    private void printHeader(String[] tableColumns) {
        String result = "|";
        for (String name : tableColumns) {
            result += name + " | ";
        }
        view.write("--------------------");
        view.write(result);
        view.write("--------------------");
    }

    private void doList() {
        String[] tableNames = manager.getTableNames();

        String message = Arrays.toString(tableNames);

        view.write(message);
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
