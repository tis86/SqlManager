package SqlCmd.controller.command;

import SqlCmd.model.DataView;
import SqlCmd.model.dbManager;
import SqlCmd.view.View;

/**
 * Created by Тарас on 03.02.2016.
 */
public class Create implements Command{

    private final dbManager manager;
    private final View view;

    public Create(dbManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if(data.length % 2 != 0) {
            throw new IllegalArgumentException("нужно четное количество параметров в формате " +
                    "'create|tableName|column1|value1|column2|value2..', а у вас: " + command);
        }

        String tableName = data[1];

        DataView dataView = new DataView();
        for (int index = 1; index < (data.length / 2); index++) {
            String columnName = data[index*2];
            String value = data[index*2 + 1];

            dataView.put(columnName, value);
        }

        manager.create(tableName, dataView);

        view.write(String.format("Запись %s была успешно создана в таблице '%s'", dataView, tableName));

    }
}
