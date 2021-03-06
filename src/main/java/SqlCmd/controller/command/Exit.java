package SqlCmd.controller.command;

import SqlCmd.view.View;

/**
 * Created by Тарас on 29.01.2016.
 */
public class Exit implements Command{

    private View view;

    public Exit(View view) {
        this.view = view;
    }

    public boolean canProcess(String command) {
        return command.equals("exit");
    }

    public void process(String command) {
        view.write("Bye!");
        throw new ExitException();
    }
}
