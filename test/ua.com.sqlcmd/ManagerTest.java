import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by Тарас on 20.01.2016.
 */
public class ManagerTest {

    private Manager manager = new Manager();

    @Before
    public void setup() {
        manager = new Manager();
        manager.connect("sqlcmd", "postgres", "trmbiq17");

    }

    @Test
    public void testGetAllTableNames() throws SQLException {
        String[] tableNames = manager.getTableNames();
        assertEquals("[user]", Arrays.toString(tableNames));
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


}
