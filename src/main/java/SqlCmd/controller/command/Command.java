package SqlCmd.controller.command;

/**
 * Created by Тарас on 29.01.2016.
 */
public interface Command {
    boolean canProcess(String command);

    void process(String command);
}
