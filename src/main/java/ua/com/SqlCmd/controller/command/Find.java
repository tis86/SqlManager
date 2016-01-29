package ua.com.SqlCmd.controller.command;

import ua.com.SqlCmd.model.DataView;
import ua.com.SqlCmd.model.dbManager;
import ua.com.SqlCmd.view.View;

/**
 * Created by Тарас on 29.01.2016.
 */
public class Find implements Command {

    private dbManager manager;
    private View view;

    public Find(View view, dbManager manager) {
        this.manager = manager;
        this.view = view;
    }

    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    public void process(String command) {
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
}
