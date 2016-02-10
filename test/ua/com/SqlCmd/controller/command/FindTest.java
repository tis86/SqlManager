package ua.com.SqlCmd.controller.command;

import SqlCmd.controller.command.Command;
import SqlCmd.controller.command.Exit;
import SqlCmd.controller.command.ExitException;
import SqlCmd.controller.command.Find;
import SqlCmd.model.DataView;
import SqlCmd.model.dbManager;
import SqlCmd.view.View;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Тарас on 10.02.2016.
 */
public class FindTest {

    private dbManager manager;
    private View view;

    @Before
    public void setup() {
        manager = mock(dbManager.class);
        view = mock(View.class);
    }

    @Test
    public void testPrintTableData() {
        //given
        Command command = new Find(view, manager);
        when(manager.getTableColumns("user"))
                .thenReturn(new String[] {"id", "name", "password"});

        DataView user1 = new DataView();
        user1.put("id", 12);
        user1.put("name", "Tarasn");
        user1.put("password", "kasdj");

        DataView user2 = new DataView();
        user2.put("id", 13);
        user2.put("name", "sasha");
        user2.put("password", "trimansdn");

        DataView[] data = new DataView[] {user1, user2};
        when(manager.getTableData("user"))
                .thenReturn(data);
        //when
        command.process("find|user");

        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals("[--------------------, |id | name | password | ," +
                " --------------------, |12 | Tarasn | kasdj | , |13 |" +
                " sasha | trimansdn | ]",captor.getAllValues().toString());

    }

    @Test
    public void testCanProcessFindString() {
        //given
        Command command = new Find(view, manager);

        //when
        boolean canProcess = command.canProcess("find|user");

        //then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongFindString() {
        //given
        Command command = new Find(view, manager);

        //when
        boolean canProcess = command.canProcess("find|qwe");

        //then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongFindWithoutParametersString() {
        //given
        Command command = new Find(view, manager);

        //when
        boolean canProcess = command.canProcess("find");

        //then
        assertFalse(canProcess);
    }

    @Test
    public void testPrintEmptyTableData() {
        //given
        Command command = new Find(view, manager);
        when(manager.getTableColumns("user"))
                .thenReturn(new String[] {"id", "name", "password"});

        DataView[] data = new DataView[0];
        when(manager.getTableData("user"))
                .thenReturn(data);
        //when
        command.process("find|user");

        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(
                "[--------------------, " +
                "|id | name | password | ," +
                " --------------------" +
                "]",captor.getAllValues().toString());

    }
}
