package integration;

import org.junit.BeforeClass;
import org.junit.Test;
import ua.com.SqlCmd.controller.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Тарас on 31.01.2016.
 */
public class IntegrationTest {

    private static ConfigurableInputStream in;
    private static ByteArrayOutputStream out;
    private String data;

    @BeforeClass
    public static void setup() {
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testHelp() {
        //given
        in.add("help");
        // in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет, введи название базы данных, имя пользователяи пароль в формате: " +
                "connect|database|userName|password\r\n" +
                "Command list: \r\n" +
                "\tconnect|databaseName|userName|password - \r\n" +
                "\t\t connect to database\r\n" +
                "\tlist - \r\n" +
                "\t\t table list database\r\n" +
                "\thelp - \r\n" +
                "\t\t write about all commands in sqlManager\r\n" +
                "\texit - \r\n" +
                "\t\t exit from sqlManager\r\n" +
                "\tfind|tableName - \r\n" +
                "\t\t get all data from 'table tableName'\r\n" +
                "Введи команду: \r\n" +
                "Bye!\r\n", getData());
    }

    public String getData() {
        try {
            return new String(out.toByteArray(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

    @Test
    public void testExit() {
        //given
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет, введи название базы данных, имя пользователяи пароль в формате: connect|database|userName|password\r\n" +
                "Bye!\r\n", getData());
    }

    @Test
    public void testListWithoutConnect() {
        //given
        in.add("list");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет, введи название базы данных, имя пользователяи пароль в формате: " +
                "connect|database|userName|password\r\n" +
                //list
                "for first time you need to use command connect|databaseName|userName|password, not 'list'\r\n" +
                "Введи команду: \r\n" +
                //exit
                "Bye!\r\n", getData());
    }

    @Test
    public void testFindWithoutConnect() {
        //given
        in.add("find|user");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет, введи название базы данных, имя пользователяи пароль в формате: " +
                "connect|database|userName|password\r\n" +
                //find|user
                "for first time you need to use command connect|databaseName|userName|password, not 'c'\r\n" +
                "Введи команду: \r\n" +
                //exit
                "Bye!\r\n", getData());
    }

    @Test
    public void testUnsupported() {
        //given
        in.add("blabla");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет, введи название базы данных, имя пользователяи пароль в формате: " +
                "connect|database|userName|password\r\n" +
                //blabla
                "for first time you need to use command connect|databaseName|userName|password, not 'blabla'\r\n" +
                "Введи команду: \r\n" +
                //exit
                "Bye!\r\n", getData());
    }

    @Test
    public void testUnsupportedAfterConnect() {
        //given
        in.add("connect|sqlcmd|postgres|postgres");
        in.add("blabla");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Привет, введи название базы данных, имя пользователяи пароль в формате: " +
                "connect|database|userName|password\r\n" +
                //connect|sqlcmd|postgres|postgres
                "Подключение к базе данных успешно!\r\n" +
                "Введи команду: \r\n" +
                //blabla
                "'blabla' is not recognized as an internal or external command\r\n" +
                "Введи команду: \r\n" +
                "Bye!\r\n", getData());
    }
}
