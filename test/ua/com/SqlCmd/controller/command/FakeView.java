package ua.com.SqlCmd.controller.command;

import SqlCmd.view.View;

/**
 * Created by Тарас on 10.02.2016.
 */
public class FakeView implements View {

    private String messages = "";

    @Override
    public void write(String message) {
        messages += message + "\n";
    }

    @Override
    public String read() {
        return null;
    }

    public String getContent() {
        return messages;
    }
}
