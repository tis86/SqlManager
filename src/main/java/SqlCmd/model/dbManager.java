package SqlCmd.model;

/**
 * Created by Тарас on 25.01.2016.
 */
public interface dbManager {
    String[] getTableNames();

    void connect(String database, String userName, String password);

    DataView[] getTableData(String tableName);

    void update(String tableName, int id, DataView newValue);

    void clear(String tableName);

    void create(String tableName, DataView input);

    String[] getTableColumns(String tableName);

    boolean isConnected();
}
