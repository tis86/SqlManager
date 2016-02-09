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
                new Create(manager, view),
                new List(view, manager),
                new Find(view, manager),
                new Unsupported(view)};
    }

    public void run() {
        try {
            doWork();
        } catch (ExitException e) {
            //do nothing
        }
    }

    private void doWork() {
        view.write("Привет, введи название базы данных, имя пользователя" +
                "и пароль в формате: connect|database|userName|password");
        while (true) {
            String input = view.read();
            if (input == null) {
                new Exit(view).process(input);
            }
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
