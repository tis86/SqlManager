package model;

import org.junit.Before;
import org.junit.Test;
import ua.com.SqlCmd.model.DataView;
import ua.com.SqlCmd.model.JDBCManager;
import ua.com.SqlCmd.model.dbManager;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Тарас on 20.01.2016.
 */
public class ManagerTest {

    private dbManager manager = new JDBCManager();

    @Before
    public void setup() {
        manager = new JDBCManager();
        manager.connect("sqlcmd", "postgres", "postgres");

    }

    @Test
    public void testGetAllTableNames() throws SQLException {
        String[] tableNames = manager.getTableNames();
        assertEquals("[user, test]", Arrays.toString(tableNames));
    }

    @Test
    public void testGetTableData() {
        //given
        manager.clear("user");

        //when
        DataView input = new DataView();
        input.put("name", "Taras");
        input.put("password", "pass");
        input.put("id", 1);
        manager.create(input);

        //then
        DataView[] users = manager.getTableData("user");
        assertEquals(1, users.length);

        DataView user = users[0];
        assertEquals("[name, password, id]", Arrays.toString(user.getNames()));
        assertEquals("[Taras, pass, 1]", Arrays.toString(user.getValues()));
    }

    @Test
    public void testUpdateTableData() {
        //given
        manager.clear("user");

        DataView input = new DataView();
        input.put("name", "Taras");
        input.put("password", "pass");
        input.put("id", 1);
        manager.create(input);

        //when
        DataView newValue = new DataView();
        newValue.put("password", "pass2");
        manager.update("user", 1, newValue);

        //then
        DataView[] users = manager.getTableData("user");
        assertEquals(1, users.length);

        DataView user = users[0];
        assertEquals("[name, password, id]", Arrays.toString(user.getNames()));
        assertEquals("[Taras, pass2, 1]", Arrays.toString(user.getValues()));
    }

    @Test
    public void testGetColumnNames() {
        //given
        manager.clear("user");

        //when
        String[] columnNames = manager.getTableColumns("user");

        //then
        assertEquals("[name, password, id]", Arrays.toString(columnNames));

    }

    @Test
    public void testisConnected() {
        //given
        //when
        //then
        assertTrue(manager.isConnected());
    }


}
