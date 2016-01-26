package ua.com.SqlCmd.model;

import java.sql.SQLException;

/**
 * Created by Тарас on 25.01.2016.
 */
public interface dbManager {
    String[] getTableNames() throws SQLException;

    void connect(String database, String userName, String password);

    DataView[] getTableData(String tableName);

    void update(String tableName, int id, DataView newValue);

    void clear(String tableName);

    void create(DataView input);
}
