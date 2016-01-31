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

    public MainController(View view, dbManager manager) {
        this.view = view;
        this.commands = new Command[]{
                new Connect(manager, view),
                new Help(view),
                new Exit(view),
                new isConnected(manager, view),
                new List(view, manager),
                new Find(view, manager),
                new Unsupported(view) };
    }

    public void run() {


        doCommand();
    }

    private void doCommand() {
        view.write("Привет, введи название базы данных, имя пользователя" +
                "и пароль в формате: connect|database|userName|password");
        while (true) {
            String input = view.read();

            for (Command command : commands) {
                if (command.canProcess(input)) {
                    command.process(input);
                    break;
                }

            }
            view.write("Введи команду: ");
        }
    }
}
