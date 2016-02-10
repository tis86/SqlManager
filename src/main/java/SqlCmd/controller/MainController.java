package SqlCmd.controller;

import SqlCmd.controller.command.*;
import SqlCmd.model.dbManager;
import SqlCmd.view.View;

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
                new Clear(manager, view),
                new Create(manager, view),
                new Unsupported(view)};
    }

    public void run() throws Exception {
        try {
            doWork();
        } catch (ExitException e) {
            //do nothing
        }
    }

    private void doWork() throws Exception {
        view.write("Привет, введи название базы данных, имя пользователя" +
                "и пароль в формате: connect|database|userName|password");
        while (true) {
            String input = view.read();

            for (Command command : commands) {
                try {
                    if (command.canProcess(input)) {
                        command.process(input);
                        break;
                    }
                } catch (Exception e) {
                    if (e instanceof ExitException) {
                        throw e;
                    }
                    printError(e);
                    break;
                }
            }
            view.write("Введи команду: ");
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
