import org.junit.Before;
import org.junit.Test;

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
    public void testGetAllTableNames() {
        String[] tableNames = manager.getTableNames();
        assertEquals("[user]", Arrays.toString(tableNames));
    }
}
