package ua.com.SqlCmd.controller;

import ua.com.SqlCmd.model.JDBCManager;
import ua.com.SqlCmd.model.dbManager;
import ua.com.SqlCmd.view.Console;
import ua.com.SqlCmd.view.View;

/**
 * Created by Тарас on 26.01.2016.
 */
public class MainController {
    public static void main(String[] args) {
        View view = new Console();
        dbManager manager = new JDBCManager();

        view.write("Привет, введи название базы данных, имя пользователя" +
                "и пароль в формате: database|userName|password");
        while (true) {
            String string = view.read();
            String[] data = string.split("\\|");
            String databaseName = data[0];
            String userName = data[1];
            String password = data[2];

            try {
                manager.connect(databaseName, userName, password);
                break;
            } catch (Exception e) {
                String message = e.getMessage();
                if (e.getCause() != null) {
                    message += " " + e.getCause().getMessage();
                }
                view.write("Неудачное подключение. Причина: " + message);
                view.write("Попробуйте еще раз");
            }
        }

        view.write("Подключение к базе данных успешно!");



    }
}
