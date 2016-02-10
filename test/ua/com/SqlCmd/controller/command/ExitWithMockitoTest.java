package ua.com.SqlCmd.controller.command;

import SqlCmd.controller.command.Command;
import SqlCmd.controller.command.Exit;
import SqlCmd.controller.command.ExitException;
import SqlCmd.view.View;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Created by Тарас on 10.02.2016.
 */
public class ExitWithMockitoTest {

    private View view = Mockito.mock(View.class);

    @Test
    public void testCanProcessExitString() {
        //given
        Command command = new Exit(view);

        //when
        boolean canProcess = command.canProcess("exit");

        //then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWrongExitString() {
        //given
        Command command = new Exit(view);

        //when
        boolean canProcess = command.canProcess("qwe");

        //then
        assertFalse(canProcess);
    }

    @Test
    public void testProcessExitCommand_throwsExitException() {
        //given
        Command command = new Exit(view);

        try {
            //when
            command.process("exit");
            fail("Expected ExitException");
        } catch (ExitException e) {
            //do nothing
        }

        //then
        Mockito.verify(view).write("Bye!");
    }

}

